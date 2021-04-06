

jmbag="0036513877"

# compile if not compiled
mvn -f lab1java compile


# compress
zip -r $jmbag lab1java/src lab1java/target lab1java/pom.xml

# move to right folder
srcfile="$jmbag.zip"
destdir="./autograder/solutions/$jmbag"
if [ ! -d "$destdir" ]; then
	mkdir -p $destdir
fi

mv $srcfile "$destdir/$srcfile"
