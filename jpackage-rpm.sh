#!/bin/bash
/usr/bin/jpackage \
-n mtgacollection \
--runtime-image ./target/jlink-image \
--module hirsizlik.mtgacollection/hirsizlik.mtgacollection.main.MTGACollectionMain \
-t rpm \
--description "A CLI program to see stats of your MTG-Arena card collection." \
--app-version $1 \
-d ./target \
--license-file ./LICENSE \
--linux-rpm-license-type GPLv3+ \
--verbose 
