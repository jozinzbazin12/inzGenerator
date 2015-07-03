package generator.models.result;

public class BasicModelData {
	private double x;
	private double y;
	private double z;
	private double sx;
	private double sy;
	private double sz;
	private double rx;
	private double ry;
	private double rz;
	private boolean isRelative;
	public double getX() {
		return x;
	}
	public void setX(double x) {
		this.x = x;
	}
	public double getY() {
		return y;
	}
	public void setY(double y) {
		this.y = y;
	}
	public double getZ() {
		return z;
	}
	public void setZ(double z) {
		this.z = z;
	}
	public double getSx() {
		return sx;
	}
	public void setSx(double sx) {
		this.sx = sx;
	}
	public double getSy() {
		return sy;
	}
	public void setSy(double sy) {
		this.sy = sy;
	}
	public double getSz() {
		return sz;
	}
	public void setSz(double sz) {
		this.sz = sz;
	}
	public double getRx() {
		return rx;
	}
	public void setRx(double rx) {
		this.rx = rx;
	}
	public double getRy() {
		return ry;
	}
	public void setRy(double ry) {
		this.ry = ry;
	}
	public double getRz() {
		return rz;
	}
	public void setRz(double rz) {
		this.rz = rz;
	}
	public boolean isRelative() {
		return isRelative;
	}
	public void setRelative(boolean isRelative) {
		this.isRelative = isRelative;
	}
		
	public void setPosition(double x, double y, double z)
	{
		this.x=x;
		this.y=y;
		this.z=z;
	}
	
	public void setScale(double sx, double sy, double sz)
	{
		this.sx=sx;
		this.sy=sy;
		this.sz=sz;
	}
	
	public void setRotation(double rx, double ry, double rz)
	{
		this.rx=rx;
		this.ry=ry;
		this.rz=rz;
	}
}
