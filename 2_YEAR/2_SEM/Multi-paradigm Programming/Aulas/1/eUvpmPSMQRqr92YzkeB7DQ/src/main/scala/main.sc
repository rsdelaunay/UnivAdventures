sealed trait MyTree[+A] //definir interface mas sem metodos

case object Empty extends MyTree[Nothing]

case class Node[A](value: A, left: MyTree[A], right: MyTree[A]) extends MyTree[A]

case class Example[A](myField: MyTree[A]) {
 def maximum() = Example.maximum(this.myField.asInstanceOf[MyTree[Int]])
 def depth() = Example.depth(this.myField)
 def map[B](f: A => B):MyTree[B] = Example.map(this.myField)(f)
}


object Example{ // ligacao com o case class
 def maximum(t: MyTree[Int]): Option[Int] = { None }
 
 def depth[A](t: MyTree[A]): Int = {
 t match {
 case Empty => 0 // caso arvore n tenha nada - profundidade será zero
 case Node(_, left, right) => 1+Math.max(depth(left), depth(right))
}}
 
def map[A,B](t: MyTree[A])(f: A => B): MyTree[B] = { //queremos ir a todos os elementos da árvore e aplicar uma funcao. Arvore A;
//funcao converte de A para B
   t match {
 case Empty => Empty //caso seja vazia, é empty
 case Node(value, left, right) => Node(f(value),map(left)(f),map(right)(f)) // caso tenha algo - aplica-se a funcao ao value e depois recursivamente
 //para a esquerda e para a direita
}
}

def main(args: Array[String]): Unit = {
 val tree1 = Node(42, Node(8, Empty, Empty), Empty)
val t = Example(tree1)
 println(s"Maximum element of the tree: ${ t.maximum()}")
 println(s"Depth of the tree: ${ t.depth()}")
 println(s"Map: ${ t.map((x:Int)=>x*2)}")
 }
}