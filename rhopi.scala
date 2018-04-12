//see LICENSE for license
//authors: Colin Schmidt, Adam Izraelevitz
package rocket

import Chisel._
import scala.collection.mutable.HashMap
import scala.collection.mutable.ArrayBuffer
import scala.util.Random

class RhoPiModule(val W : Int = 64) extends Module {
  //val W = 64
  val io = new Bundle { 
    val state_i = Vec.fill(25){Bits(INPUT, W)}
    val state_o = Vec.fill(25){Bits(OUTPUT,W)}
  }


  for(i <- 0 until 5) {
    for(j <- 0 until 5) {
      val temp = Bits()
      if((RHOPI.tri(i*5+j)%W) == 0){
        temp := io.state_i(i*5+j)
      }else{
        temp := Cat(io.state_i(i*5+j)((W-1) - (RHOPI.tri(i*5+j)-1)%W,0),io.state_i(i*5+j)(W-1,W-1 - ((RHOPI.tri(i*5+j)-1)%W)))
      }
      io.state_o(j*5+((2*i+3*j)%5)) := temp
    }
  }
}
