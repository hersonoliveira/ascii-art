package com.ascii

import com.sksamuel.scrimage.Image
import org.slf4j.LoggerFactory

object Main extends App {

  override def main(args: Array[String]): Unit = {

    val logger = LoggerFactory.getLogger(getClass)

    val imagePath = "/ascii-pineapple.jpg"
    val image = Image.fromResource(imagePath)
    logger.info("Successfully loaded image")
    logger.info(s"Image size: ${image.width} x ${image.height}")

    val pixelMatrix = loadPixelsToMatrix(image)
    logger.info("RGB matrix created")
    val brightnessMatrix = convertToBrightnessMatrix(pixelMatrix)
    logger.info("Brightness matrix created")
    val asciiMatrix = convertBrightnessToAscii(brightnessMatrix)
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
    for {
      arr <- matrix
    } yield arr map (tuple => (tuple._1 + tuple._2 + tuple._3) / 3)
  }

  def convertBrightnessToAscii(matrix: Array[Array[Int]]): Array[Array[Char]] = {
    val chars = "`^\",:;Il!i~+_-?][}{1)(|\\/tfjrxnuvczXYUJCLQ0OZmwqpdbkhao*#MW&8%B@$"
    val toCharNum = (num: Int) => { (num * chars.size) / 255 }
    for {
      arr <- matrix
    } yield arr map (num => chars charAt toCharNum(num))
  }
}
