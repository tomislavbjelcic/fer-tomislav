

jmbag="0036513877"
projdir="lab2java"
autograderdir="autograder_lab2"

# compile if not compiled
mvn -f $projdir compile


# compress
zip -r $jmbag $projdir/src $projdir/target $projdir/pom.xml

# move to right folder
srcfile="$jmbag.zip"
destdir="./$autograderdir/solutions/$jmbag"
if [ ! -d "$destdir" ]; then
	mkdir -p $destdir
fi

mv $srcfile "$destdir/$srcfile"
