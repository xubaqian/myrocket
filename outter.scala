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

  val sha3_init = Reg(init=Bool(false))
  val aindex = Reg(init = UInt(0,5))

  val s_idle:: s_absorb :: s_squeeze :: Nil = Enum(UInt(), 3)
  val state = Reg(init=s_idle)

  switch(state){
    is(s_idle){
      when(io.ready && !io.busy){
        /// 进入吸收阶段，设为忙
        io.valid := false
        io.busy := true
        state := s_absorb
      }
    }
    is(s_absorb){
      when(!io.kill){
        when(aindex < UInt(24)){
          /// 计算，每一轮的常数不同，好像是和索引对应。输入数据和索引
          aindex := aindex + UInt(1)
        }.otherwise{
          /// 计数值回零，重置矩阵
          aindex := UInt(0)
          sha3_init := true
          //这里还应该把sha3_init输入
          state := s_squeeze
        }
      }.otherwise{
        io.valid := false
        io.busy := false
        state := s_idle
      }
    }
    is(s_squeeze){
      io.busy := false
      state := s_idle
      when(!io.kill){
        sha3_init := false
        ///还有一步是接输出
        io.valid := true
      }.otherwise{
        io.valid := false
      }
    }
  }

}

