import Presentation.Validator
import cats.data.Validated.{Invalid, Valid}
import org.scalatest.matchers.must.Matchers.be
import org.scalatest.matchers.should.Matchers.convertToAnyShouldWrapper
import org.scalatest.wordspec.AnyWordSpecLike


class ValidatorSpec extends AnyWordSpecLike {

  "ArgumentValidator" when {
    "Number of arguments is false will fail" in {
      Validator(List("path_to_XML", "accurate_pass", "extra argument")).validateArguments.isValid should be(false)
    }

    "Path file is valid will load" in {
      val args = List("src/test/resources/valid.xml", "accurate_pass")
      Validator(args).validateXMLFile(args) match {
        case Valid(_) => succeed
        case Invalid(errors) => fail(s"This case should not happen")
      }
    }

    "Path file is invalid will fail" in {
      val args = List("src/test/resources", "accurate_pass")
      Validator(args).validateXMLFile(args).isValid should be(false)
    }

    "XML file is valid will parse data" in {
      val args = List("src/test/resources/valid.xml", "accurate_pass")
      Validator(args).parseDocument match {
        case Valid(_) => succeed
        case Invalid(errors) => fail(s"This case should not happen")
      }
    }

    "XML file is invalid (wrong property type for Stat) will fail" in {
      val args = List("src/test/resources/invalid.xml", "accurate_pass")
      Validator(args).parseDocument.isValid should be(false)
    }

  //TODO: Add more parsing tests
  }
}