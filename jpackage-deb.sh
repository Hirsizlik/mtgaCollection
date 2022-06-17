#!/bin/bash
jpackage \
-n mtgacollection \
--runtime-image ./target/jlink-image \
--module hirsizlik.mtgacollection/hirsizlik.mtgacollection.main.MTGACollectionMain \
-t deb \
--description "A CLI program to see stats of your MTG-Arena card collection." \
--app-version $1 \
-d ./target \
--license-file ./LICENSE \
--verbose 
