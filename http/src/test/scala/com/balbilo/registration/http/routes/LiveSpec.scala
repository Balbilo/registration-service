package com.balbilo.registration.http.routes

import akka.http.scaladsl.model.StatusCodes
import akka.http.scaladsl.testkit.ScalatestRouteTest
import com.balbilo.test.AnyWordSpecBase
import org.scalatest.prop.TableDrivenPropertyChecks._
class LiveSpec extends AnyWordSpecBase with ScalatestRouteTest {

  val liveEndPoint = "/live"

  "Live" should {

    "return status OK for GET request" in {
      Get(liveEndPoint) ~> Live.route ~> check {
        status shouldBe StatusCodes.OK
      }
    }

    "return status for all other requests" in {
      val invalidMethods = Table(
        ("Request", "Method"),
        (Post(liveEndPoint), "POST"),
        (Put(liveEndPoint), "PUT"),
        (Delete(liveEndPoint), "DELETE"),
        (Patch(liveEndPoint), "PATCH")
      )

      forAll(invalidMethods) { (request, expectedMethod) =>
        request ~> Live.route ~> check {
          status shouldBe StatusCodes.MethodNotAllowed
        }
      }
    }

  }

}
