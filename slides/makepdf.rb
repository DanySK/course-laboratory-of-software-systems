log_file_name = "hugo.log"
`touch #{log_file_name}`
log_file = File.open(log_file_name, "r")
log_file.seek(0,IO::SEEK_END)
server = fork {
    exec("hugo server --log --logFile #{log_file_name}")
}
contents = Dir["content/*/_index.md"].map { |f|
    File.expand_path("..", f).split('/').last
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
    pdf_name = target || "00-introduction"
    target ||= ""
    puts "Using decktape to generate #{pdf_name}.pdf from /#{target}"
    `decktape http://localhost:1313/Course-Laboratory-of-Software-Systems/#{target} #{pdf_name}.pdf`
end

generate_slides nil
for pack in contents
    generate_slides pack
end
# Site is generated and is being server. Launch decktape
`decktape http://localhost:1313/Course-Laboratory-of-Software-Systems/ slides.pdf`
# Terminate the server
Process.kill("TERM", server)
Process.wait(server)
File.delete(log_file)
