trait Generator[+T] {
  self => //an alias for "this"

  def generate: T

  def map[S](f: T => S): Generator[S] = new Generator[S] {
    override def generate: S = f(self.generate)
    //IMPORTANT -> self.generator refers to trait's function
    // this.generate would have made a recursive call
    // Generator.this.generate = self.generate
  }
}