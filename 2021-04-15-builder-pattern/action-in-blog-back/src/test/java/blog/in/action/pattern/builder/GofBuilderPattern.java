package blog.in.action.pattern.builder;

public class GofBuilderPattern {

	public static void main(String[] args) {
		LabtopDirector director = new LabtopDirector(new ConcreteLabtopBuilder());
		Labtop lowSpecLabtop = director.getLowSpecLabtop();
		System.out.println("CPU: " + lowSpecLabtop.getCpu() + ", RAM: " + lowSpecLabtop.getRam());
		Labtop highSpecLabtop = director.getHighSpecLabtop();
		System.out.println("CPU: " + highSpecLabtop.getCpu() + ", RAM: " + highSpecLabtop.getRam());
	}
}

enum CPU {
	INTEL_I3, INTEL_I5, INTEL_I7;
}

enum RAM {
	SAMSUNG_8, SAMSUNG_16, SAMSUNG_32;
}

class LabtopDirector {

	private final LabtopBuilder builder;

	public LabtopDirector(LabtopBuilder builder) {
		this.builder = builder;
	}

	public Labtop getLowSpecLabtop() {
		builder.setCpu(CPU.INTEL_I3);
		builder.setRam(RAM.SAMSUNG_8);
		return builder.getLabtop();
	}

	public Labtop getMiddleSpecLabtop() {
		builder.setCpu(CPU.INTEL_I5);
		builder.setRam(RAM.SAMSUNG_16);
		return builder.getLabtop();
	}

	public Labtop getHighSpecLabtop() {
		builder.setCpu(CPU.INTEL_I3);
		builder.setRam(RAM.SAMSUNG_32);
		return builder.getLabtop();
	}
}

interface LabtopBuilder {

	void setCpu(CPU cpu);

	void setRam(RAM ram);

	Labtop getLabtop();
}

class ConcreteLabtopBuilder implements LabtopBuilder {

	private CPU cpu;

	private RAM ram;

	@Override
	public void setCpu(CPU cpu) {
		this.cpu = cpu;
	}

	@Override
	public void setRam(RAM ram) {
		this.ram = ram;
	}

	@Override
	public Labtop getLabtop() {
		Labtop labtop = new Labtop();
		labtop.setCpu(cpu);
		labtop.setRam(ram);
		return labtop;
	}
}

class Labtop {

	private CPU cpu;

	private RAM ram;

	public CPU getCpu() {
		return cpu;
	}

	public void setCpu(CPU cpu) {
		this.cpu = cpu;
	}

	public RAM getRam() {
		return ram;
	}

	public void setRam(RAM ram) {
		this.ram = ram;
	}
}