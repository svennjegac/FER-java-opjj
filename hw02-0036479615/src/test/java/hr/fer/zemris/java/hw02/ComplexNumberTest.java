package hr.fer.zemris.java.hw02;

import static org.junit.Assert.*;
import static hr.fer.zemris.java.hw02.ComplexNumber.*;
import org.junit.Test;

public class ComplexNumberTest {

	@Test
	public void constructor() {
		ComplexNumber cp = new ComplexNumber(2, 5);
		assertEquals(2, cp.getReal(), 1E-3);
		assertEquals(5, cp.getImaginary(), 1E-3);
	}
	
	@Test
	public void constructor1() {
		ComplexNumber cp = new ComplexNumber(0, 0);
		assertEquals(0, cp.getReal(), 1E-3);
		assertEquals(0, cp.getImaginary(), 1E-3);
	}
	
	@Test
	public void factory0() {
		ComplexNumber cp = fromReal(3);
		assertEquals(3, cp.getReal(), 1E-3);
		assertEquals(0, cp.getImaginary(), 1E-3);
	}
	
	@Test
	public void factory1() {
		ComplexNumber cp = fromImaginary(2);
		assertEquals(0, cp.getReal(), 1E-3);
		assertEquals(2, cp.getImaginary(), 1E-3);
	}
	
	@Test
	public void fromMagnitudeAndAngle0() {
		ComplexNumber cp = fromMagnitudeAndAngle(2d, 1.57);
		assertEquals(0.00159, cp.getReal(), 1E-3);
		assertEquals(2d, cp.getImaginary(), 1E-3);
	}
	
	@Test
	public void fromMagnitudeAndAngle1() {
		ComplexNumber cp = fromMagnitudeAndAngle(0d, 1.57);
		assertEquals(0.0, cp.getReal(), 1E-3);
		assertEquals(0.0, cp.getImaginary(), 1E-3);
	}
	
	@Test
	public void getRealImaginary() {
		ComplexNumber cp = fromMagnitudeAndAngle(2d, 1.57);
		assertEquals(0.00159, cp.getReal(), 1E-3);
		assertEquals(2d, cp.getImaginary(), 1E-3);
	}
	
	@Test
	public void getMagnitudeAndAngle0() {
		ComplexNumber cp = fromMagnitudeAndAngle(2d, 1.57);
		assertEquals(2., cp.getMagnitude(), 1E-3);
		assertEquals(1.57, cp.getAngle(), 1E-3);
	}
	
	@Test
	public void add() {
		ComplexNumber cp = new ComplexNumber(5, 1);
		ComplexNumber cp2 = new ComplexNumber(3, 3);
		ComplexNumber cp3 = cp.add(cp2);
		assertEquals(8d, cp3.getReal(), 1E-3);
		assertEquals(4d, cp3.getImaginary(), 1E-3);
	}
	
	@Test
	public void sub() {
		ComplexNumber cp = new ComplexNumber(5, 1);
		ComplexNumber cp2 = new ComplexNumber(3, 3);
		ComplexNumber cp3 = cp.sub(cp2);
		assertEquals(2d, cp3.getReal(), 1E-3);
		assertEquals(-2d, cp3.getImaginary(), 1E-3);
	}
	
	@Test
	public void mul() {
		ComplexNumber cp = new ComplexNumber(5, 1);
		ComplexNumber cp2 = new ComplexNumber(3, 3);
		ComplexNumber cp3 = cp.mul(cp2);
		assertEquals(12d, cp3.getReal(), 1E-3);
		assertEquals(18d, cp3.getImaginary(), 1E-3);
	}
	
	@Test
	public void div0() {
		ComplexNumber cp = new ComplexNumber(5, 1);
		ComplexNumber cp2 = new ComplexNumber(3, 3);
		ComplexNumber cp3 = cp.div(cp2);
		assertEquals(1, cp3.getReal(), 1E-3);
		assertEquals(-0.6666, cp3.getImaginary(), 1E-3);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void div1() {
		ComplexNumber cp = new ComplexNumber(5, 1);
		ComplexNumber cp2 = new ComplexNumber(0, 0);
		ComplexNumber cp3 = cp.div(cp2);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void power0() {
		ComplexNumber cp = new ComplexNumber(2, 3);
		ComplexNumber cp2 = cp.power(-1);
		assertEquals(6554d, cp2.getReal(), 1E-3);
		assertEquals(4449d, cp2.getImaginary(), 1E-3);
	}
	
	@Test
	public void power() {
		ComplexNumber cp = new ComplexNumber(2, 3);
		ComplexNumber cp2 = cp.power(7);
		assertEquals(6554d, cp2.getReal(), 1E-3);
		assertEquals(4449d, cp2.getImaginary(), 1E-3);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void root0() {
		ComplexNumber cp = new ComplexNumber(2, 3);
		ComplexNumber[] cp2 = cp.root(0);
		assertEquals(1.4519, cp2[0].getReal(), 1E-3);
		assertEquals(0.4934, cp2[0].getImaginary(), 1E-3);
		assertEquals(-1.1532, cp2[1].getReal(), 1E-3);
		assertEquals(1.0106, cp2[1].getImaginary(), 1E-3);
		assertEquals(-0.299, cp2[2].getReal(), 1E-3);
		assertEquals(-1.504, cp2[2].getImaginary(), 1E-3);
	}
	
	@Test
	public void root1() {
		ComplexNumber cp = new ComplexNumber(2, 3);
		ComplexNumber[] cp2 = cp.root(1);
		assertEquals(2, cp2[0].getReal(), 1E-3);
		assertEquals(3, cp2[0].getImaginary(), 1E-3);
	}
	
	@Test
	public void root() {
		ComplexNumber cp = new ComplexNumber(2, 3);
		ComplexNumber[] cp2 = cp.root(3);
		assertEquals(1.4519, cp2[0].getReal(), 1E-3);
		assertEquals(0.4934, cp2[0].getImaginary(), 1E-3);
		assertEquals(-1.1532, cp2[1].getReal(), 1E-3);
		assertEquals(1.0106, cp2[1].getImaginary(), 1E-3);
		assertEquals(-0.299, cp2[2].getReal(), 1E-3);
		assertEquals(-1.504, cp2[2].getImaginary(), 1E-3);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void parseError0() {
		parse("x");
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void parseError1() {
		parse("*");
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void parseError2() {
		parse("+");
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void parseError3() {
		parse("-");
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void parseError4() {
		parse("2--1");
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void parseError5() {
		parse("--1");
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void parseError6() {
		parse("i-");
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void parseError7() {
		parse("1+i+1");
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void parseError8() {
		parse("1+1+x");
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void parseError9() {
		parse("1+1");
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void parseError10() {
		parse("i+i");
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void parseError11() {
		parse("1+i+");
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void parseError12() {
		parse("1+i+i");
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void parseError13() {
		parse("1-i-x");
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void parseError14() {
		parse("-e");
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void parseError15() {
		parse("2+");
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void parseError16() {
		parse("i-");
	}
	
	@Test
	public void parseEmpty() {
		ComplexNumber cp = parse("");
		assertEquals(0d, cp.getReal(), 1E-1);
		assertEquals(0d, cp.getImaginary(), 1E-1);
	}

	@Test
	public void cupicExample1() {
		ComplexNumber cp = parse("3.51");
		assertEquals(3.51, cp.getReal(), 1E-1);
		assertEquals(0d, cp.getImaginary(), 1E-1);
	}

	@Test
	public void cupicExample2() {
		ComplexNumber cp = parse("-3.17");
		assertEquals(-3.17, cp.getReal(), 1E-1);
		assertEquals(0d, cp.getImaginary(), 1E-1);
	}

	@Test
	public void cupicExample3() {
		ComplexNumber cp = parse("-2.71i");
		assertEquals(0d, cp.getReal(), 1E-1);
		assertEquals(-2.71, cp.getImaginary(), 1E-1);
	}

	@Test
	public void cupicExample4() {
		ComplexNumber cp = parse("i");
		assertEquals(0, cp.getReal(), 1E-1);
		assertEquals(1d, cp.getImaginary(), 1E-1);
	}

	@Test
	public void cupicExample5() {
		ComplexNumber cp = parse("1");
		assertEquals(1, cp.getReal(), 1E-1);
		assertEquals(0d, cp.getImaginary(), 1E-1);
	}

	@Test
	public void cupicExample6() {
		ComplexNumber cp = parse("-2.71-3.15i");
		assertEquals(-2.71, cp.getReal(), 1E-1);
		assertEquals(-3.15, cp.getImaginary(), 1E-1);
	}

	@Test
	public void parseReal() {
		ComplexNumber cp = parse("2");
		assertEquals(2d, cp.getReal(), 1E-1);
		assertEquals(0d, cp.getImaginary(), 1E-1);
	}

	@Test
	public void parseRealPlus() {
		ComplexNumber cp = parse("+2");
		assertEquals(2d, cp.getReal(), 1E-1);
		assertEquals(0d, cp.getImaginary(), 1E-1);
	}

	@Test
	public void parseRealMinus() {
		ComplexNumber cp = parse("-2");
		assertEquals(-2d, cp.getReal(), 1E-1);
		assertEquals(0d, cp.getImaginary(), 1E-1);
	}

	@Test
	public void parseRealMinusDecimal() {
		ComplexNumber cp = parse("-2.");
		assertEquals(-2d, cp.getReal(), 1E-1);
		assertEquals(0d, cp.getImaginary(), 1E-1);
	}

	@Test
	public void parseRealMinusDecimalv2() {
		ComplexNumber cp = parse("-2.11");
		assertEquals(-2.11, cp.getReal(), 1E-1);
		assertEquals(0d, cp.getImaginary(), 1E-1);
	}

	@Test
	public void parseRealDecimal() {
		ComplexNumber cp = parse("2.");
		assertEquals(2d, cp.getReal(), 1E-1);
		assertEquals(0d, cp.getImaginary(), 1E-1);
	}

	@Test
	public void parseRealDecimalv2() {
		ComplexNumber cp = parse("2.2");
		assertEquals(2.2, cp.getReal(), 1E-1);
		assertEquals(0d, cp.getImaginary(), 1E-1);
	}

	@Test
	public void parseRealPlusDecimal() {
		ComplexNumber cp = parse("+2.2");
		assertEquals(2.2, cp.getReal(), 1E-1);
		assertEquals(0d, cp.getImaginary(), 1E-1);
	}

	@Test
	public void parseRealPlusDecimalv2() {
		ComplexNumber cp = parse("+2.");
		assertEquals(2., cp.getReal(), 1E-1);
		assertEquals(0d, cp.getImaginary(), 1E-1);
	}

	@Test
	public void parseImaginary() {
		ComplexNumber cp = parse("i");
		assertEquals(0d, cp.getReal(), 1E-1);
		assertEquals(1d, cp.getImaginary(), 1E-1);
	}

	@Test
	public void parseImaginaryPlus() {
		ComplexNumber cp = parse("+i");
		assertEquals(0d, cp.getReal(), 1E-1);
		assertEquals(1d, cp.getImaginary(), 1E-1);
	}

	@Test
	public void parseImaginary1() {
		ComplexNumber cp = parse("1i");
		assertEquals(0d, cp.getReal(), 1E-1);
		assertEquals(1d, cp.getImaginary(), 1E-1);
	}

	@Test
	public void parseImaginaryPlus1() {
		ComplexNumber cp = parse("+1i");
		assertEquals(0d, cp.getReal(), 1E-1);
		assertEquals(1d, cp.getImaginary(), 1E-1);
	}

	@Test
	public void parseImaginaryMinus() {
		ComplexNumber cp = parse("-i");
		assertEquals(0d, cp.getReal(), 1E-1);
		assertEquals(-1d, cp.getImaginary(), 1E-1);
	}

	@Test
	public void parseImaginaryMinus1() {
		ComplexNumber cp = parse("-1i");
		assertEquals(0d, cp.getReal(), 1E-1);
		assertEquals(-1d, cp.getImaginary(), 1E-1);
	}

	@Test
	public void parseImaginaryDecimal() {
		ComplexNumber cp = parse(".2i");
		assertEquals(0d, cp.getReal(), 1E-1);
		assertEquals(.2, cp.getImaginary(), 1E-1);
	}

	@Test
	public void parseImaginaryDecimalv2() {
		ComplexNumber cp = parse("2.2i");
		assertEquals(0d, cp.getReal(), 1E-1);
		assertEquals(2.2, cp.getImaginary(), 1E-1);
	}

	@Test
	public void parseImaginaryPlusDecimal() {
		ComplexNumber cp = parse("+.2i");
		assertEquals(0d, cp.getReal(), 1E-1);
		assertEquals(.2, cp.getImaginary(), 1E-1);
	}

	@Test
	public void parseImaginaryPlusDecimalv2() {
		ComplexNumber cp = parse("+2.2i");
		assertEquals(0d, cp.getReal(), 1E-1);
		assertEquals(2.2, cp.getImaginary(), 1E-1);
	}

	@Test
	public void parseImaginaryMinusDecimal() {
		ComplexNumber cp = parse("-.2i");
		assertEquals(0d, cp.getReal(), 1E-1);
		assertEquals(-.2, cp.getImaginary(), 1E-1);
	}

	@Test
	public void parseImaginaryMinusDecimalv2() {
		ComplexNumber cp = parse("-44.2444i");
		assertEquals(0d, cp.getReal(), 1E-1);
		assertEquals(-44.2444, cp.getImaginary(), 1E-1);
	}

	@Test
	public void parseRealImaginary() {
		ComplexNumber cp = parse("0+0i");
		assertEquals(0d, cp.getReal(), 1E-1);
		assertEquals(0d, cp.getImaginary(), 1E-1);
	}

	@Test
	public void parseRealImaginary2() {
		ComplexNumber cp = parse("+0+0i");
		assertEquals(0d, cp.getReal(), 1E-1);
		assertEquals(0d, cp.getImaginary(), 1E-1);
	}

	@Test
	public void parseRealImaginary3() {
		ComplexNumber cp = parse("+0-0i");
		assertEquals(0d, cp.getReal(), 1E-1);
		assertEquals(0d, cp.getImaginary(), 1E-1);
	}

	@Test
	public void parseRealImaginary4() {
		ComplexNumber cp = parse("-0+0i");
		assertEquals(0d, cp.getReal(), 1E-1);
		assertEquals(0d, cp.getImaginary(), 1E-1);
	}

	@Test
	public void parseRealImaginary5() {
		ComplexNumber cp = parse("-0-0i");
		assertEquals(0d, cp.getReal(), 1E-1);
		assertEquals(0d, cp.getImaginary(), 1E-1);
	}

	@Test
	public void parseRealImaginary6() {
		ComplexNumber cp = parse("2+0i");
		assertEquals(2d, cp.getReal(), 1E-1);
		assertEquals(0d, cp.getImaginary(), 1E-1);
	}

	@Test
	public void parseRealImaginary7() {
		ComplexNumber cp = parse("2+3i");
		assertEquals(2d, cp.getReal(), 1E-1);
		assertEquals(3d, cp.getImaginary(), 1E-1);
	}

	@Test
	public void parseRealImaginary8() {
		ComplexNumber cp = parse("2-0i");
		assertEquals(2d, cp.getReal(), 1E-1);
		assertEquals(0d, cp.getImaginary(), 1E-1);
	}

	@Test
	public void parseRealImaginary9() {
		ComplexNumber cp = parse("2+0.98i");
		assertEquals(2d, cp.getReal(), 1E-1);
		assertEquals(0.98, cp.getImaginary(), 1E-1);
	}

	@Test
	public void parseRealImaginary10() {
		ComplexNumber cp = parse("2.34+11.12i");
		assertEquals(2.34, cp.getReal(), 1E-1);
		assertEquals(11.12, cp.getImaginary(), 1E-1);
	}

	@Test
	public void parseRealImaginary11() {
		ComplexNumber cp = parse("2.34-11.12i");
		assertEquals(2.34, cp.getReal(), 1E-1);
		assertEquals(-11.12, cp.getImaginary(), 1E-1);
	}

	@Test
	public void parseRealImaginary12() {
		ComplexNumber cp = parse("-2.34+11.12i");
		assertEquals(-2.34, cp.getReal(), 1E-1);
		assertEquals(11.12, cp.getImaginary(), 1E-1);
	}

	@Test
	public void parseRealImaginary13() {
		ComplexNumber cp = parse("-2.34-11.12i");
		assertEquals(-2.34, cp.getReal(), 1E-1);
		assertEquals(-11.12, cp.getImaginary(), 1E-1);
	}

	@Test
	public void parseRealImaginary14() {
		ComplexNumber cp = parse("-.34-.12i");
		assertEquals(-.34, cp.getReal(), 1E-1);
		assertEquals(-.12, cp.getImaginary(), 1E-1);
	}

	@Test
	public void parseRealImaginary15() {
		ComplexNumber cp = parse("112.6i+17.99");
		assertEquals(17.99, cp.getReal(), 1E-1);
		assertEquals(112.6, cp.getImaginary(), 1E-1);
	}

	@Test
	public void parseRealImaginary16() {
		ComplexNumber cp = parse("-112.6i-17.99");
		assertEquals(-17.99, cp.getReal(), 1E-1);
		assertEquals(-112.6, cp.getImaginary(), 1E-1);
	}

	@Test
	public void parseRealImaginary17() {
		ComplexNumber cp = parse("-.6i+17.99");
		assertEquals(17.99, cp.getReal(), 1E-1);
		assertEquals(-.6, cp.getImaginary(), 1E-1);
	}

	@Test
	public void parseRealImaginary18() {
		ComplexNumber cp = parse("-.6i-.99");
		assertEquals(-.99, cp.getReal(), 1E-1);
		assertEquals(-.6, cp.getImaginary(), 1E-1);
	}

	@Test
	public void parseRealImaginary19() {
		ComplexNumber cp = parse("-.0i+17.99");
		assertEquals(17.99, cp.getReal(), 1E-1);
		assertEquals(0d, cp.getImaginary(), 1E-1);
	}
}
