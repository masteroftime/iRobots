
public class Message 
{
	private String type;
	private int x;
	private int y;
	private String id; 
	private double angle;
	
	public Message(String id, String type, int x, int y, double angle)
	{
		this.id = id;
		this.type = type;
		this.x = x;
		this.y = y;
		this.angle = angle;
	}

	public double getAngle() {
		return angle;
	}

	public void setAngle(double angle) {
		this.angle = angle;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return "Message [type=" + type + ", x=" + x + ", y=" + y + ", id=" + id
				+ "]";
	}
}
