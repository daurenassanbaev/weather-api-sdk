#!/bin/bash

if [ "$#" -ne 1 ]; then
  echo "Usage: $0 <new_version>"
  exit 1
fi

NEW_VERSION=$1

echo "Updating version to ${NEW_VERSION}..."
mvn versions:set -DnewVersion=${NEW_VERSION} || { echo "Failed to update version in pom.xml"; exit 1; }

find . -name "*pom.xml.versionsBackup" -exec rm {} \;

echo "Committing changes..."
git add pom.xml
git commit -m "Release version ${NEW_VERSION}" || { echo "Git commit failed"; exit 1; }

echo "Creating tag v${NEW_VERSION}..."
git tag -a v${NEW_VERSION} -m "Release version ${NEW_VERSION}" || { echo "Failed to create tag"; exit 1; }

echo "Pushing changes to repository..."
git push || { echo "Git push failed"; exit 1; }
git push --tags || { echo "Git push tags failed"; exit 1; }

echo "Release version ${NEW_VERSION} successfully published!"