//> using scala "3.3.6"
//> using dep "org.typelevel::cats-effect:3.6.1"
package javaplusplus


def main(args: Array[String]): Unit = {
  // #region map
  List(1, 2, 3, 4, 5, 6)
    .filter(_ % 2 == 0)
    .map(v => v * v)
    .foreach(println)
  // #endregion
  
  // TODO: more features/examples
}
