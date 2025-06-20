error id: AEA6BDB7162AB46298034D1E72B793AB
file://<WORKSPACE>/Main.scala
### java.lang.AssertionError: assertion failed: attempt to parse java.lang.Object from classfile

occurred in the presentation compiler.



action parameters:
offset: 9
uri: file://<WORKSPACE>/Main.scala
text:
```scala
object Ma@@

```


presentation compiler configuration:
Scala version: 3.7.1-bin-nonbootstrapped
Classpath:
<WORKSPACE>/snake/.bloop/snake/bloop-bsp-clients-classes/classes-Metals-CCBOTZCzSrKgUg6fId76Ww== [exists ], <HOME>/Library/Caches/bloop/semanticdb/com.sourcegraph.semanticdb-javac.0.10.4/semanticdb-javac-0.10.4.jar [exists ], <HOME>/Library/Caches/Coursier/v1/https/repo1.maven.org/maven2/org/scala-lang/scala3-library_3/3.7.1/scala3-library_3-3.7.1.jar [exists ], <HOME>/Library/Caches/Coursier/v1/https/repo1.maven.org/maven2/org/scalafx/scalafx_3/24.0.0-R35/scalafx_3-24.0.0-R35.jar [exists ], <HOME>/Library/Caches/Coursier/v1/https/repo1.maven.org/maven2/org/scala-lang/scala-library/2.13.16/scala-library-2.13.16.jar [exists ], <HOME>/Library/Caches/Coursier/v1/https/repo1.maven.org/maven2/org/openjfx/javafx-base/24/javafx-base-24.jar [exists ], <HOME>/Library/Caches/Coursier/v1/https/repo1.maven.org/maven2/org/openjfx/javafx-controls/24/javafx-controls-24.jar [exists ], <HOME>/Library/Caches/Coursier/v1/https/repo1.maven.org/maven2/org/openjfx/javafx-fxml/24/javafx-fxml-24.jar [exists ], <HOME>/Library/Caches/Coursier/v1/https/repo1.maven.org/maven2/org/openjfx/javafx-graphics/24/javafx-graphics-24.jar [exists ], <HOME>/Library/Caches/Coursier/v1/https/repo1.maven.org/maven2/org/openjfx/javafx-media/24/javafx-media-24.jar [exists ], <HOME>/Library/Caches/Coursier/v1/https/repo1.maven.org/maven2/org/openjfx/javafx-swing/24/javafx-swing-24.jar [exists ], <HOME>/Library/Caches/Coursier/v1/https/repo1.maven.org/maven2/org/openjfx/javafx-web/24/javafx-web-24.jar [exists ], <HOME>/Library/Caches/Coursier/v1/https/repo1.maven.org/maven2/org/openjfx/javafx-base/24/javafx-base-24-mac-aarch64.jar [exists ], <HOME>/Library/Caches/Coursier/v1/https/repo1.maven.org/maven2/org/openjfx/javafx-controls/24/javafx-controls-24-mac-aarch64.jar [exists ], <HOME>/Library/Caches/Coursier/v1/https/repo1.maven.org/maven2/org/openjfx/javafx-fxml/24/javafx-fxml-24-mac-aarch64.jar [exists ], <HOME>/Library/Caches/Coursier/v1/https/repo1.maven.org/maven2/org/openjfx/javafx-graphics/24/javafx-graphics-24-mac-aarch64.jar [exists ], <HOME>/Library/Caches/Coursier/v1/https/repo1.maven.org/maven2/org/openjfx/javafx-media/24/javafx-media-24-mac-aarch64.jar [exists ], <HOME>/Library/Caches/Coursier/v1/https/repo1.maven.org/maven2/org/openjfx/javafx-swing/24/javafx-swing-24-mac-aarch64.jar [exists ], <HOME>/Library/Caches/Coursier/v1/https/repo1.maven.org/maven2/org/openjfx/javafx-web/24/javafx-web-24-mac-aarch64.jar [exists ], <WORKSPACE>/snake/.bloop/snake/bloop-bsp-clients-classes/classes-Metals-CCBOTZCzSrKgUg6fId76Ww==/META-INF/best-effort [missing ]
Options:
-Xsemanticdb -sourceroot <WORKSPACE>/snake -Ywith-best-effort-tasty




#### Error stacktrace:

```
scala.runtime.Scala3RunTime$.assertFailed(Scala3RunTime.scala:8)
	dotty.tools.dotc.core.classfile.ClassfileParser$AbstractConstantPool.getSuperClass(ClassfileParser.scala:175)
	dotty.tools.dotc.core.classfile.ClassfileParser.parseParents$1(ClassfileParser.scala:380)
	dotty.tools.dotc.core.classfile.ClassfileParser.parseClass(ClassfileParser.scala:395)
	dotty.tools.dotc.core.classfile.ClassfileParser.$anonfun$1(ClassfileParser.scala:302)
	dotty.tools.dotc.core.classfile.ClassfileParser.run(ClassfileParser.scala:297)
	dotty.tools.dotc.core.ClassfileLoader.doComplete(SymbolLoaders.scala:471)
	dotty.tools.dotc.core.SymbolLoader.complete(SymbolLoaders.scala:402)
	dotty.tools.dotc.core.SymDenotations$SymDenotation.completeFrom(SymDenotations.scala:175)
	dotty.tools.dotc.core.SymDenotations$SymDenotation.completeOnce(SymDenotations.scala:385)
	dotty.tools.dotc.core.SymDenotations$SymDenotation.isAbsent(SymDenotations.scala:615)
	dotty.tools.dotc.interactive.Completion$.isValidCompletionSymbol(Completion.scala:340)
	dotty.tools.dotc.interactive.Completion$Completer.dotty$tools$dotc$interactive$Completion$Completer$$include(Completion.scala:658)
	dotty.tools.dotc.interactive.Completion$Completer$$anon$5.applyOrElse(Completion.scala:687)
	dotty.tools.dotc.interactive.Completion$Completer$$anon$5.applyOrElse(Completion.scala:686)
	scala.collection.immutable.List.collect(List.scala:268)
	scala.collection.immutable.List.collect(List.scala:79)
	dotty.tools.dotc.interactive.Completion$Completer.accessibleMembers(Completion.scala:688)
	dotty.tools.dotc.interactive.Completion$Completer.scopeCompletions$$anonfun$1(Completion.scala:412)
	scala.runtime.function.JProcedure1.apply(JProcedure1.java:15)
	scala.runtime.function.JProcedure1.apply(JProcedure1.java:10)
	scala.collection.IterableOnceOps.foreach(IterableOnce.scala:619)
	scala.collection.IterableOnceOps.foreach$(IterableOnce.scala:617)
	dotty.tools.dotc.core.Contexts$Context$$anon$2.foreach(Contexts.scala:135)
	dotty.tools.dotc.interactive.Completion$Completer.scopeCompletions(Completion.scala:402)
	dotty.tools.dotc.interactive.Completion$.scopeContext(Completion.scala:59)
	dotty.tools.pc.IndexedContext$LazyWrapper.<init>(IndexedContext.scala:91)
	dotty.tools.pc.IndexedContext$.apply(IndexedContext.scala:80)
	dotty.tools.pc.HoverProvider$.hover(HoverProvider.scala:52)
	dotty.tools.pc.ScalaPresentationCompiler.hover$$anonfun$1(ScalaPresentationCompiler.scala:433)
```
#### Short summary: 

java.lang.AssertionError: assertion failed: attempt to parse java.lang.Object from classfile