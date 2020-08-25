#!/usr/bin/env ruby

# Configuration options
website_address = 'http://localhost:1313/Course-Laboratory-of-Software-Systems/' # always add the trailing slash
decktape_options = '-s 1440x900 -p 20'

# Workaround for decktape bug
puts 'Working around bug: https://github.com/astefanutti/decktape/issues/220'
puts 'By replacing max vieport relative sizes 95/80 with 120/140'

shortcode_path = 'layouts/shortcodes/image.html'
custom_image_shortcode = File.read(shortcode_path)
replace_width = custom_image_shortcode.gsub(/}}\s*95\s*{{/, '}}120{{')
replace_both = replace_width.gsub(/}}\s*80\s*{{/, '}}140{{')
File.open(shortcode_path, 'w') { | file | file.puts replace_both }

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

def generate_slides(decktape_command, target)
    proposed_name = target&.gsub('/', '-') || ""
    pdf_name = proposed_name&.empty? ? "00-introduction" : proposed_name
    puts "Using decktape to generate #{pdf_name}.pdf from /#{target}"
    `#{decktape_command}#{target} #{pdf_name}.pdf`
end

decktape_command_base = "decktape #{decktape_options} #{website_address}"
for pack in contents
    generate_slides(decktape_command_base, pack)
end
# Site has been generated and is being served. Launch decktape
puts `#{decktape_command_base} slides.pdf`
# Terminate the server
Process.kill("TERM", server)
begin
    Process.wait(server)
rescue SystemCallError
    puts "The server terminated before I could wait for it."
end
File.delete(log_file)

# Undo the bug workaround
puts 'Undoing the bug workaround'
File.open(shortcode_path, 'w') { | file | file.puts custom_image_shortcode }
puts 'Done.'