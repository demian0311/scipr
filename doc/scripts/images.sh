#!/bin/sh

# this is me trying to figure out how images are sent back
# from a web server.

echo "\n*****************************************************"
echo "this is how nginx does it"
echo "\n*****************************************************"
curl --verbose http://neidetcher.com/assets/icons/flickr.png

echo "\n*****************************************************"
echo "this is how scipr does it"
echo "\n*****************************************************"
curl --verbose http://localhost:8099/assets/icons/flickr.png 
echo "\n*****************************************************"
