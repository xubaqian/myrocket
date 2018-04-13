package rocket

import rocket.ALU.SZ_ALU_FN
//import Node._
import scala.collection.mutable.HashMap
import scala.collection.mutable.ArrayBuffer
import scala.util.Random
import uncore.devices._
import uncore.util.CacheName
import uncore.constants._
import uncore.tilelink2._
import util._
import Chisel.ImplicitConversions._
import Chisel._
import config._

class Outter(implicit p: Parameters) extends CoreModule()(p) {
  val io  = new Bundle {
    val in0 = UInt(INPUT, 64)
    val in1 = UInt(INPUT, width = 64)
    val ready = Bool(INPUT)
    val kill  = Bool(INPUT)

    val out = UInt(width = 64)
    val valid = Bool(OUTPUT)
    val busy  = Bool(OUTPUT)
  }

  val cst =  UInt("b111")
  val round = 24
  val i = UInt()

  when(io.ready && !io.kill){
    for (i <- 0 until round) {
      io.out := Cat(io.in0, io.in1) ^ cst
    }
  }

  when(io.kill){
    io.out := 0
    io.valid := 0
    io.busy := 0
  }

  if (i == round){
    io.valid := 1
    io.busy := 0
  }else{
    io.valid := 0
    io.busy := 1
  }

}
