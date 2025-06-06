private def checkCollaboration(artist1: Artist, artist2: Artist): String = {
  val startYear = Math.max(artist1.startYear, artist2.startYear)
  val endYear = Math.min(artist1.endYear, artist2.endYear)

  if (startYear <= endYear) {
    s"${artist1.name} and ${artist2.name} could have collaborated between $startYear and $endYear"
  } else {
    s"${artist1.name} and ${artist2.name} couldn't have collaborated"
  }
}
