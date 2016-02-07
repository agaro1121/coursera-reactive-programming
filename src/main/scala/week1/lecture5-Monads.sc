import scala.util.Try

def aa = Try(1 +2)
def bb = Try(5 /0)

for {
  a <- aa
  b <- bb
} yield a + b