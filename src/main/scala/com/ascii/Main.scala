package com.ascii

import java.nio.file.Paths

import com.sksamuel.scrimage.Image
import org.slf4j.LoggerFactory

object Main extends App {

  lazy val logger = LoggerFactory.getLogger(getClass)

  lazy val DefaultImage = "/toad.jpg"

  override def main(args: Array[String]): Unit = {

    val image = getImage(args)
    logger.info("Successfully loaded image")
    logger.info(s"Image size: ${image.width} x ${image.height}")

    val pixelMatrix = loadPixelsToMatrix(image)
    logger.info("RGB matrix created")
    val brightnessMatrix = convertToBrightnessMatrix(pixelMatrix)
    logger.info("Brightness matrix created")
    val asciiMatrix = convertBrightnessToAscii(brightnessMatrix)
    printAsciiMatrix(asciiMatrix, image.width, image.height)
  }

  def getImage(args: Array[String]): Image = args match {
    case Array(path, _*) => Image.fromPath(Paths.get(path))
    case _ => Image.fromResource(DefaultImage)
  }

  def loadPixelsToMatrix(image: Image): Array[Array[(Int, Int, Int)]] = {
    val width = image.width
    val height = image.height
    val matrix = Array.ofDim[(Int, Int, Int)](width, height)
    for (i <- 0 to width - 1) {
      for (j <- 0 to height - 1) {
        val pixel = image.pixel(i, j)
        matrix(i)(j) = (pixel.red, pixel.green, pixel.blue)
      }
    }
    matrix
  }

  def convertToBrightnessMatrix(matrix: Array[Array[(Int, Int, Int)]]): Array[Array[Int]] = {
    matrix map { row => row map (tuple => (tuple._1 + tuple._2 + tuple._3) / 3) }
  }

  def convertBrightnessToAscii(matrix: Array[Array[Int]]): Array[Array[Char]] = {
    val chars = "`^\",:;Il!i~+_-?][}{1)(|\\/tfjrxnuvczXYUJCLQ0OZmwqpdbkhao*#MW&8%B@$"
    val toCharNum = (num: Int) => {
      (num * (chars.size - 1)) / 255
    }
    matrix map { row => row map (num => chars charAt toCharNum(num)) }
  }

  def printAsciiMatrix(matrix: Array[Array[Char]], width: Int, height: Int): Unit = {
    for (i <- 0 to height - 1) {
      for (j <- 0 to width - 1) {
        print(s"${matrix(j)(i)}${matrix(j)(i)}${matrix(j)(i)}")
      }
      println
    }
  }
}
