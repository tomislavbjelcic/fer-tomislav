

jmbag="0036513877"

# compress
zip -r $jmbag lab1java/src lab1java/pom.xml

# move to right folder
srcfile="$jmbag.zip"
destdir="./autograder/solutions/$jmbag"
if [ ! -d "$destdir" ]; then
	mkdir -p $destdir
fi

mv $srcfile "$destdir/$srcfile"
