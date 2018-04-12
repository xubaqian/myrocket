//see LICENSE for license
//authors: Colin Schmidt, Adam Izraelevitz
package rocket

import Chisel._
import scala.collection.mutable.HashMap
import scala.collection.mutable.ArrayBuffer
import scala.util.Random

class IotaModule(val W: Int = 64) extends Module {
  val io = new Bundle { 
    val state_i = Vec.fill(5*5){Bits(INPUT,W)}
    val state_o = Vec.fill(5*5){Bits(OUTPUT,W)}
    val round = UInt(INPUT, 5)
  }

  //TODO: c code uses look up table for this
  for(i <- 0 until 5) {
    for(j <- 0 until 5) {
      if(i !=0 || j!=0)
        io.state_o(i*5+j) := io.state_i(i*5+j)
    }
  }
  //val const = ROUND_CONST.value(io.round)
  val const = IOTA.round_const(io.round)
  debug(const)
  io.state_o(0) := io.state_i(0) ^ const
}

/*
object iotaMain { 
  def main(args: Array[String]): Unit = {
    //chiselMainTest(Array[String]("--backend", "c", "--genHarness", "--compile", "--test"),
    chiselMainTest(args,
    () => Module(new IotaModule())){c => new IotaModuleTests(c)
    }
  }
}*/

