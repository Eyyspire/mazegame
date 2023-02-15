all :
	make cls
	make docs
	make tests

cls :
	javac -sourcepath src -d classes src/**/*.java
	javac -sourcepath src -d classes src/**/*/*.java

tests :
	javac -classpath test4poo.jar test/**/*.java
	javac -classpath test4poo.jar test/**/*/*.java

docs:
	javadoc -sourcepath src -d doc src/**/*.java

game:
	java -classpath classes mazegame/GameMain

jar:
	jar cvfe game.jar mazegame.GameMain -C classes mazegame

clean:
	rm -rf classes
	rm -rf doc
	rm test/mazegame/**/*.class
	rm test/mazegame/MazeTest.class
	rm game.jar
