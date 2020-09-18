# 枚举
java语法糖的一种，继承抽象类Enum。
```java
public enum EnumTest {

    CASE1,
    CASE2,
    CASE3;

    public static void main(String[] args) {
        System.out.println(EnumTest.CASE1);
    }
}
```
反汇编
```java
public final class EnumTest extends Enum
{

	public static final EnumTest CASE1;
	public static final EnumTest CASE2;
	public static final EnumTest CASE3;
	private static final EnumTest $VALUES[];

	public static EnumTest[] values()
	{
		return (EnumTest[])$VALUES.clone();
	}

	public static EnumTest valueOf(String name)
	{
		return (EnumTest)Enum.valueOf(com/github/AllenDuke/EnumTest, name);
	}

	private EnumTest(String s, int i)
	{
		super(s, i);
	}

	static 
	{
		CASE1 = new EnumTest("CASE1", 0);
		CASE2 = new EnumTest("CASE2", 1);
		CASE3 = new EnumTest("CASE3", 2);
		$VALUES = (new EnumTest[] {
			CASE1, CASE2, CASE3
		});
	}
}
```