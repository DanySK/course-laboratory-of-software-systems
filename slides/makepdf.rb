#!/usr/bin/env ruby

# Configuration options

class Generator
    def generate(source = '', suffix = '', base_url = 'http://localhost:1313/Course-Laboratory-of-Software-Systems', options = '')
        pdf_name = "#{(source.empty? ? '00-introduction' : source).gsub('/', '-') || ''}_#{suffix}.pdf"
        puts "Using #{self.class.name} to generate #{pdf_name}.pdf from /#{source}"
        generation_command(
            "#{base_url}/#{source}",
            pdf_name,
            options
        )
    end
end
class DecktapeGenerator < Generator
    def generation_command(url, file_name, options)
        options += '-s 1440x900 -p 20'
        puts "url: #{url}, file_name: #{file_name}, options: #{options}"
        command = "decktape #{options} #{url} #{file_name}"
        puts "Launching: #{command}"
        puts `#{command}`
    end
end
class ChromiumGenerator < Generator

    def initialize
        puts "Searching for the Chromium or Google Chrome executable"
        command = `bash -c "eval compgen -c"`
            .split(/\n/)
            .filter { | it | /.*chrom(?:e|ium).*/ =~ it }
            .uniq
            .filter { | it | /.*driver.*/ !~ it }
            .sort
        puts "Found candidates: #{command}"    
        if command.empty? then
            raise RuntimeError.new 'No candidate for chrome / chromium'
        end
        @chromium = command.filter { | it | /.*chromium.*/ =~ it }.first || command.first
        puts "Selected #{@chromium}"
    end

    def generation_command(url, file_name, options)
        options += '--disable-gpu --virtual-time-budget=10000 --window-size=1440,900'
        command = "#{@chromium} --headless --run-all-compositor-stages-before-draw #{options} --print-to-pdf=#{file_name} #{url}?print-pdf"
        puts "Launching: #{command}"
        puts `#{command}`
    end
end

chromium = ChromiumGenerator.new
decktape = DecktapeGenerator.new

# Workaround for decktape bug
puts 'Working around bug: https://github.com/astefanutti/decktape/issues/220'
puts 'By replacing max vieport relative sizes 95/80 with 120/140'

log_file_name = "hugo.log"
`touch #{log_file_name}`
log_file = File.open(log_file_name, "r")
log_file.seek(0, IO::SEEK_END)
server = fork {
    exec("hugo server --log --logFile #{log_file_name}")
}
contents = Dir["content/**/_index.md"].map { |f|
    f.split('/')[1...-1].join('/')
}
puts "Contents will be generated for #{contents}"
puts "Server launched, monitoring log!"
Process.detach(server)
until log_file.gets =~ /.*Watching for config changes in.*/
    puts "Server is still generating..."
    sleep(1)
    select([log_file])
end
puts "Generation complete!"

# Site has been generated and is being served. Launch generation

for pack in [''] + contents do
    # decktape.generate(pack, 'decktape')
    chromium.generate(pack, 'chromium')
end

# Terminate the server
Process.kill("TERM", server)
begin
    Process.wait(server)
rescue SystemCallError
    puts "The server terminated before I could wait for it."
end
File.delete(log_file)
