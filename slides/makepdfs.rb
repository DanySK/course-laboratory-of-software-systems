#!/usr/bin/env ruby
command_base = %{
    google-chrome-stable
        --headless
        --run-all-compositor-stages-before-draw
        --disable-gpu
        --window-size=1440,900
        --disable-web-security
        --virtual-time-budget=1000000
        --print-to-pdf=
}.gsub(/\s+/, " ").strip
command_end = '?print-pdf&pdfSeparateFragments=false'

root = ARGV[0] || raise("Bad usage, missing argument: launch as ./makepdfs.rb ROOT_OF_THE_WEBSITE")
puts "Working inside #{root}"
remote = `git -C '#{root}' remote get-url origin`
puts "Detected remote: #{remote}"
url = remote.match(/^(git@|https:\/\/)github\.com(\/|:)(?<owner>[^\/]+)\/(?<repo>[^\/]+?)(\.git)?$/)
owner = url[:owner]
repo = url[:repo] == "#{owner}.github.io" ? "" : "/#{url[:repo]}"
puts "Detected repo: #{owner}/#{repo}"
files = Dir.glob("#{root}/**/index.html")
puts "Detected the following HTML roots:"
puts files
paths = Hash[
    files.map { |path|
        route = path.sub(root, '').gsub(/^\/?(.*)\/index.html$/) { $1 }
        [route.nil? || route.strip.empty? ? "index" : route.gsub('/', '_'), "https://#{owner}.github.io#{repo}/#{route}#{command_end}"]
    }
]

def is_letter_format(file)
    `pdfinfo #{file} | grep 'Page size'`.include?('letter')
end

max_attempts = 25
max_attempts_format = 10

for name, path in paths do
    puts "Working on #{name} built from #{path}"
    output = "#{name}_slides.pdf"
    command = "timeout 2m #{command_base}#{output} '#{path}'"
    attempt = 0
    attempts_for_format = 1
    size = 0
    is_letter = true
    while (size / 1024 < 3 || is_letter) && attempt < max_attempts && attempts_for_format < max_attempts_format do
        attempt = attempt + 1
        puts "ATTEMPT #{attempt} at #{Time.now}: launching #{command}"
        `#{command}`
        size = File.size?(output) || 0
        is_letter = size > 1024 && is_letter_format(output)
        if is_letter then attempts_for_format += 1 end
        puts "Produced a file of #{size} bytes with the #{is_letter ? 'wrong' : 'correct'} format"
    end
    if attempt >= max_attempts then
        puts "::error ::Giving up after #{max_attempts} attempts, the URL #{path} produced an invalid file too many times."
        File.delete(output)
    end
    if attempts_for_format >= max_attempts_format then
        puts "::warning ::Website at #{path} does not seem to be rendering slides, saving as other document"
        File.rename(output, "#{name}_page_format.pdf")
    end
end
