package org.yaml.cobrayaml

import org.scalatest.FlatSpec
import org.yaml.snakeyaml.Yaml

class Test extends FlatSpec {

  "yamls" should "load a Set" in {
    val yamlset = "!!set {a: null, b: null}"
    val yamls = Yamls()
    val yaml = new Yaml()
    yamls.load(yamlset) === Set("a", "b")
    println(yaml.load(yamlset).getClass)
  }

  it should "load a Seq" in {
    val yamlseq = "[a, b, c]"
    val yamls = Yamls()
    yamls.load(yamlseq) === Seq("a", "b", "c")
  }

  it should "load a Map" in {
    val yamlmap = "{a: 1, b: 2}"
    val yamls = Yamls()
    yamls.load(yamlmap) === Map("a" -> 1, "b" -> 2)
  }
}
