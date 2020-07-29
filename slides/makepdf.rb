#!/usr/bin/env ruby

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

def generate_slides(target)
    proposed_name = target&.gsub('/', '-') || ""
    pdf_name = proposed_name&.empty? ? "00-introduction" : proposed_name
    puts "Using decktape to generate #{pdf_name}.pdf from /#{target}"
    `decktape -s 1440x900 http://localhost:1313/Course-Laboratory-of-Software-Systems/#{target} #{pdf_name}.pdf`
end

for pack in contents
    generate_slides pack
end
# Site has been generated and is being served. Launch decktape
`decktape http://localhost:1313/Course-Laboratory-of-Software-Systems/ slides.pdf`
# Terminate the server
Process.kill("TERM", server)
begin
    Process.wait(server)
rescue SystemCallError
    puts "The server terminated before I could wait for it."
end
File.delete(log_file)
