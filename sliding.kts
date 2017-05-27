import org.otfried.cs109.readString

fun makeField(rows: Int, cols: Int): Array<Array<Int>> {
  var m = Array (rows) {Array(cols) {0}}
  var s = 0
  for (i in 0..rows-1) {
    for (j in 0..cols-1) {
      s += 1
	  m[i][j] = s   
	}
  }
  m[rows-1][cols-1] = 0
  return m
}

fun displayField(field: Array<Array<Int>>): String {
  var out = ""
  for (j in 1..field.size) {
	for (i in 1..field[0].size) if (field[j-1][i-1]!= 0) out += "o----o " else out += "       "
	out += "\n"
	for (i in 1..field[0].size) if (field[j-1][i-1]!= 0) out += "|    | " else out += "       "
	out += "\n"
	for (i in 1..field[0].size) {
	  if (field[j-1][i-1]!= 0) out += "| %2d | ".format(field[j-1][i-1])
	  else out += "       "
	  }
	out += "\n"
	for (i in 1..field[0].size) if (field[j-1][i-1]!= 0) out += "|    | " else out += "       "
	out += "\n"
    for (i in 1..field[0].size) if (field[j-1][i-1]!= 0) out += "o----o " else out += "       "
	out += "\n"
  }
  println(out)
  return out
}

data class Pos(val row: Int, val col: Int)

fun findEmpty(field: Array<Array<Int>>): Pos {
  var a = 0
  var b = 0
  for (i in 0 ..field.size-1){
    for (j in 0..field[0].size-1) 
      if (field[i][j] == 0) {a += i; b += j}
    }
  return Pos(a, b)	
}

fun makeMove(field: Array<Array<Int>>, delta: Pos) {
  val rows = field.size
  val cols = field[0].size
  val now = findEmpty(field)
  val upd = Pos(now.row + delta.row, now.col + delta.col)
  if (upd.row <= (rows-1) && upd.row >=0 ) {
    if (upd.col <= (cols-1) && upd.col >= 0){
      field[now.row][now.col] = field[upd.row][upd.col]
      field[upd.row][upd.col] = 0  
    }
  }
}

val random = java.util.Random()

fun shuffle(field: Array<Array<Int>>, iter: Int) {
  val moves = arrayOf(Pos(1,0), Pos(-1,0), Pos(0,1), Pos(0,-1))
  for (i in 1 .. iter) {
    val mov = moves[random.nextInt(4)]
    makeMove(field, mov)
  }
}

fun strToMove(s: String): Pos {
  when (s.trim().toLowerCase()) {
    "left", "l" -> return Pos(0, 1)
	"right", "r" -> return Pos(0, -1)
	"up", "u" -> return Pos(1, 0)
	"down", "dn", "d" ->  return Pos(-1, 0)
	else -> return Pos(0, 0)
  }
}

fun playGame(rows: Int, cols: Int) {
  val f = makeField(rows, cols)
  shuffle(f, 1000)
  while (true) {
    displayField(f)
    val s = readString("What is your move> ")
    println()
    if (s == "quit")
      return
    val move = strToMove(s)
    makeMove(f, move)
  }
}

if (args.size>1) playGame(args[0].toInt(), args[1].toInt())
else playGame(4, 4)