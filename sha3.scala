//see LICENSE for license
//authors: Colin Schmidt, Adam Izraelevitz
package rocket

import Chisel._
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
import config._

class Sha3Module(implicit p: Parameters) extends CoreModule {
  //constants
  val W = 64
  val S = 1
  val r = 2*256
  val c = 25*W - r
  val round_size_words = c/W
  val rounds = 24 //12 + 2l
  val hash_size_words = 256/W
  val bytes_per_word = W/8

  val io = new Bundle { 
    val absorb = Bool(INPUT)
    val init   = Bool(INPUT)
    val round  = UInt(INPUT,width=5)
    val aindex = UInt(INPUT,width=log2Up(round_size_words))
    val message_in = Bits(INPUT, width = W)
    //val hash_out = Vec.fill(hash_size_words){Bits(OUTPUT, width = W)}
    val hash_out = Vec(25, Bits(OUTPUT, W ))
    //val hash_out = UInt(OUTPUT, W)
    //val hash_out = Vec(hash_size_words, UInt(OUTPUT, width = W))

  }
  //val state = Vec.fill(5*5){Reg(init = Bits(0, width = W))}

  /**val state = Vec.fill(5*5){Reg(init = Bits(0, width = W))}

  println("\n\n\nMy sha3:\n"
    + io.absorb + "\n"
    + io.init   + "\n"
    + io.round  + "\n"
    + io.aindex + "\n"
    + io.message_in + "\n"
    + io.hash_out + "\n"
    + state     + "\n"
    + "\n\n")*/
  //submodules

  //println("\n\n\nSha3_RhoPi:" + rhopi.state_i + "\n" + rhopi.state_o + "\n\n\n")

  /**
  val theta = Module(new ThetaModule(W)).io
  val rhopi = Module(new RhoPiModule(W)).io
  val chi   = Module(new ChiModule(W)).io
  val iota  = Module(new IotaModule(W))

  //default
  //theta.state_i := Vec.fill(25){Bits(0,W)}
  iota.io.round     := UInt(0)

  //connect submodules to each other
    if(S == 1){
      theta.state_i := state
      rhopi.state_i <> theta.state_o
      chi.state_i   <> rhopi.state_o
      iota.io.state_i  <> chi.state_o
      state         := iota.io.state_o
    }


  iota.io.round    := io.round


  when(io.absorb){
    state := state
    when(io.aindex < UInt(round_size_words)){
      state((io.aindex%UInt(5))*UInt(5)+(io.aindex/UInt(5))) := 
        state((io.aindex%UInt(5))*UInt(5)+(io.aindex/UInt(5))) ^ io.message_in
    }
  }

  val hash_res = Vec.fill(hash_size_words){Bits(width = W)}
  for( i <- 0 until hash_size_words){
    io.hash_out(i) := state(i*5)
  }

  //initialize state to 0 for new hashes or at reset
  when(io.init){
    state := Vec.fill(5*5){Bits(0, width = W)}
  }
*/
}
