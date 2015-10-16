Cobrayaml
==========
Read Yaml in Scala. This is a thin Scala wrapper on top of [Snakeyaml](https://bitbucket.org/asomov/snakeyaml). It conveniently replaces Java collections with Scala equivalents.
See `Test` for some examples.

Examples
--------

    scala> val yamlset = "!!set {a: null, b: null}"
    yamlset: String = !!set {a: null, b: null}
    
    scala> val yamlseq = "[a, b, c]"
    yamlseq: String = [a, b, c]
    
    scala> val yamlmap = "{a: 1, b: 2}"
    yamlmap: String = {a: 1, b: 2}

with Snakeyaml:

    scala> import org.yaml.snakeyaml._
    import org.yaml.snakeyaml._
    
    scala> val yaml = new Yaml()
    yaml: org.yaml.snakeyaml.Yaml = Yaml:1809119839
    
    scala> val b = yaml.load(yamlset).isInstanceOf[Set[_]]
    b: Boolean = false

    scala> val b = yaml.load(yamlseq).isInstanceOf[Seq[_]]
    b: Boolean = false

    scala> val b = yaml.load(yamlmap).isInstanceOf[Map[_, _]]
    b: Boolean = false

with Cobrayaml:

    scala> import org.yaml.cobrayaml._
    import org.yaml.cobrayaml._
    
    scala> val yamls = Yamls()
    yamls: org.yaml.snakeyaml.Yaml = Yaml:115501637
    
    scala> val b = yamls.load(yamlset).isInstanceOf[Set[_]]
    b: Boolean = true

    scala> val b = yamls.load(yamlseq).isInstanceOf[Seq[_]]
    b: Boolean = true

    scala> val b = yamls.load(yamlmap).isInstanceOf[Map[_, _]]
    b: Boolean = true

Installation
------------

This library is built against Scala 2.11.7. Clone this repository, then:

    sbt publishLocal
    
In your `build.sbt` add:

    libraryDependencies += "org.yaml" %% "cobrayaml" % VERSION
