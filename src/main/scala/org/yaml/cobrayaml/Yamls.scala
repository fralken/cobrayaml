package org.yaml.cobrayaml

import org.yaml.snakeyaml.Yaml
import org.yaml.snakeyaml.constructor.{ AbstractConstruct, Constructor }
import org.yaml.snakeyaml.nodes.{ MappingNode, Node, SequenceNode, Tag }
import org.yaml.snakeyaml.representer.{ Represent, Representer }

import scala.collection.JavaConversions._
import scala.reflect.ClassTag

object Yamls {
  def apply() = new Yaml(new ScalaConstructor, new ScalaRepresenter)

  def apply[T](implicit c: ClassTag[T]) = new Yaml(
    new ScalaConstructor(c.runtimeClass.asInstanceOf[Class[_ <: AnyRef]]),
    new ScalaRepresenter)
}

class ScalaRepresenter extends Representer {
  multiRepresenters.put(classOf[Set[_ <: AnyRef]], new RepresentSet)
  multiRepresenters.put(classOf[Seq[_ <: AnyRef]], new RepresentSeq)
  multiRepresenters.put(classOf[Map[_ <: AnyRef, _ <: AnyRef]], new RepresentMap)

  private class RepresentSet extends Represent {
    override def representData(data: AnyRef): Node = {
      val scalaSet = data.asInstanceOf[Set[_ <: AnyRef]].map(s => s -> null).toMap
      representMapping(getTag(scalaSet.getClass, Tag.SET), scalaSet, null)
    }
  }

  private class RepresentSeq extends Represent {
    override def representData(data: AnyRef): Node = {
      val scalaSeq = data.asInstanceOf[Seq[_ <: AnyRef]]
      representSequence(getTag(scalaSeq.getClass, Tag.SEQ), scalaSeq, null)
    }
  }

  private class RepresentMap extends Represent {
    override def representData(data: AnyRef): Node = {
      val scalaMap = data.asInstanceOf[Map[_ <: AnyRef, _ <: AnyRef]]
      representMapping(getTag(scalaMap.getClass, Tag.MAP), scalaMap, null)
    }
  }
}

class ScalaConstructor(theRoot: Class[_ <: AnyRef]) extends Constructor(theRoot) {

  def this() = this(classOf[AnyRef])

  yamlConstructors.put(Tag.SET, new ConstructSet)
  yamlConstructors.put(Tag.SEQ, new ConstructSeq)
  yamlConstructors.put(Tag.MAP, new ConstructMap)

  private class ConstructSet extends AbstractConstruct {
    override def construct(node: Node): AnyRef = {
      val setNode = node.asInstanceOf[MappingNode]
      val s = for {
        tuple <- setNode.getValue
        keyNode = tuple.getKeyNode
      } yield constructObject(keyNode)
      s.toSet
    }
  }

  private class ConstructSeq extends AbstractConstruct {
    override def construct(node: Node): AnyRef = {
      val seqNode = node.asInstanceOf[SequenceNode]
      val s = for (child <- seqNode.getValue) yield constructObject(child)
      s.toSeq
    }
  }

  private class ConstructMap extends AbstractConstruct {
    override def construct(node: Node): AnyRef = {
      val mapNode = node.asInstanceOf[MappingNode]
      val m = for {
        tuple <- mapNode.getValue
        keyNode = tuple.getKeyNode
        valueNode = tuple.getValueNode
      } yield constructObject(keyNode) -> constructObject(valueNode)
      m.toMap
    }
  }
}
