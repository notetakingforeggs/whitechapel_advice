#!/bin/bash

echo "Starting scraper at $(date)" >> /home/jonah/development/code/whitechapel_advice/logs/scraper-debug.log

export PATH=/usr/local/sbin:/usr/local/bin:/usr/bin:/sbin:/bin

cd /home/jonah/development/code/whitechapel_advice || exit 1

# log free memory prior
free -h >> /home/jonah/development/code/whitechapel_advice/logs/scraper-debug.log

/usr/local/bin/docker compose run --rm scraper >> /home/jonah/development/code/whitechapel_advice/logs/scraper.log 2>&1

echo "finished scraper at $(date)" >> /home/jonah/development/code/whitechapel_advice/logs/scraper-debug.log
