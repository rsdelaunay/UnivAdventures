object RegimeOPT extends Enumeration { //objeto regime extende enumeracao
  type RegimeOPT = Value //regime opt valor
  val Ordinario, TrabEstud = Value 
}

case class Turma(id: String, alunos: Turma.Alunos) {
  def trabs(): Turma.Alunos = Turma.trabs(this)
  def searchStudent(n: Turma.Numero): Option[Turma.Aluno] = Turma.searchStudent(n, this)
  def finalGrade(n: Turma.Numero): Option[Float] = Turma.finalGrade(this, n)
}


object Turma {
  type Nome = String
  type Numero = Int
  type NT = Option[Float]
  type NP = Option[Float]
  type Regime = RegimeOPT.RegimeOPT
  type Aluno = (Numero, Nome, Regime, NT, NP)
  type Alunos = List[Aluno]

  def trabs(t: Turma): Alunos = {
    t.alunos.filter { case (_, _, regime, _, _) => regime == RegimeOPT.TrabEstud }
  }

//searchStudent that performs the search for a student by their student number. Note,
//the type returned is Option[Aluno]
  def searchStudent(n: Numero, t: Turma): Option[Aluno] = {
    t.alunos.find{ case (num, _, _, _, _) => num == n}
  }

  // Calcula a nota final
  def finalGrade(t: Turma, numero: Numero): Option[Float] = {
    searchStudent(numero, t) match {
      case Some((_, _, _, Some(nt), Some(np))) if nt >= 9.5 && np >= 9.5 => Some(0.6f * nt + 0.4f * np) //caso tenha notas e sejam positivas, calcula
      case _ => None //retorna none se nao tiver notas positivas ou o aluno n existir
    }
  }
  
}

//  `object Main` tem que estar FORA do `object Turma`
object Main {
  def main(args: Array[String]): Unit = {
    val t: Turma = Turma("id22", List((11, "Tiaguinho", RegimeOPT.Ordinario, Some(8), Some(20)),
      (12, "Jose", RegimeOPT.TrabEstud, Some(13), None),
      (13, "Maria", RegimeOPT.TrabEstud, None, Some(15)),
      (10, "Olé" , RegimeOPT.TrabEstud, Some(8), Some(20))
    ))

    println("Workers-students: " + t.trabs())  
  }
}


