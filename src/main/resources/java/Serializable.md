# jdk中序列化的几种方式
要注意序列化id。
## 实现Serializable 隐式
会自动序列化所有非static和 transient关键字修饰的成员变量。
## 实现实现Externalizable接口 显式序列化可控序列化
重写writeExternal, readExternal。
```java
	@Override
	public void writeExternal(ObjectOutput out) throws IOException {
		// TODO Auto-generated method stub
		System.out.println("Blip.writeExternal");
		out.writeObject(s);
		out.writeInt(i);
//		
	}
	@Override
	public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
		// TODO Auto-generated method stub
		System.out.println("Blip.readExternal");
		s = (String)in.readObject();
		i = in.readInt();
```
## 实现Serializable接口 添加writeObject()和readObject()方法 显+隐序列化
1. 必须要被private修饰                                ----->才能被调用
2. 第一行调用默认的defaultRead/WriteObject(); ----->隐式序列化非static和transient
3. 调用read/writeObject()将获得的值赋给相应的值  --->显式序列化
```java
	private  void writeObject(ObjectOutputStream stream) throws IOException {
		stream.defaultWriteObject();
		stream.writeInt(age);
	}
	private void readObject(ObjectInputStream stream) throws ClassNotFoundException, IOException {
		stream.defaultReadObject();
		age = stream.readInt();
	}
```