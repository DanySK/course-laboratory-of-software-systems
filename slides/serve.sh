#!/bin/sh
trap "exit" INT TERM ERR
trap "kill 0" EXIT
hugo server &
while [ -e /proc/$! ]; do
    inotifywait -e modify,create,delete -r content shared-slides && sleep 1 && shared-slides/preprocess.rb;
done &
wait
