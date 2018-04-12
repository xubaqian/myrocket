//see LICENSE for license
//authors: Colin Schmidt, Adam Izraelevitz
package rocket

import Chisel._
import scala.collection.mutable.HashMap
import scala.collection.mutable.ArrayBuffer


class ThetaModule(val W: Int = 64) extends Module {
  //val W = 64
  //val W = RangeParam(64, 8, 64, 8).register(this, "W")
  val io = new Bundle { 
    val state_i = Vec.fill(5*5){Bits(INPUT, width = W)}
    val state_o = Vec.fill(5*5){Bits(OUTPUT,width = W)}
  }

  val bc = Vec.fill(5){Bits(width = W)}
  for(i <- 0 until 5) {
    bc(i) := io.state_i(i*5+0) ^ io.state_i(i*5+1) ^ io.state_i(i*5+2) ^ io.state_i(i*5+3) ^ io.state_i(i*5+4)
  }

  for(i <- 0 until 5) {
    val t = Bits(width = W)
    t := bc((i+4)%5) ^ common.ROTL(bc((i+1)%5), UInt(1), UInt(W))
    for(j <- 0 until 5) {
      io.state_o(i*5+j) := io.state_i(i*5+j) ^ t
    }
  }
}

class Parity extends Module {
  val io = new Bundle {
    val in = Vec.fill(5){Bool(INPUT)}
    val res = Bool(OUTPUT)
  }
  io.res := io.in(0) ^ io.in(1) ^ io.in(2) ^ io.in(3) ^ io.in(4)
}
