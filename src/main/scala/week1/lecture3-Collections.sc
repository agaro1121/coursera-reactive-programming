/***
  pat <- expr

  x <- expr withFilter {
    case pat => true
    case _ => false //these don't get added to the generator
  } map {
    case pat => x //represents the yield
  }

  */