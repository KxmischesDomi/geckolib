package software.bernie.geckolib3.geo.render.built;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;

import software.bernie.geckolib3.core.processor.IBone;
import software.bernie.geckolib3.core.snapshot.BoneSnapshot;

public class GeoBone implements IBone {
	public GeoBone parent;

	public List<GeoBone> childBones = new ArrayList<>();
	public List<GeoCube> childCubes = new ArrayList<>();

	public String name;
	private BoneSnapshot initialSnapshot;

	public Boolean mirror;
	public Double inflate;
	public Boolean dontRender;
	public boolean isHidden;
	// I still have no idea what this field does, but its in the json file so
	// ¯\_(ツ)_/¯
	public Boolean reset;

	private float scaleX = 1;
	private float scaleY = 1;
	private float scaleZ = 1;

	private float positionX;
	private float positionY;
	private float positionZ;

	public float rotationPointX;
	public float rotationPointY;
	public float rotationPointZ;

	private float rotateX;
	private float rotateY;
	private float rotateZ;

	public Object extraData;

	@Override
	public void setModelRendererName(String modelRendererName) {
		this.name = modelRendererName;
	}

	@Override
	public void saveInitialSnapshot() {
		if (this.initialSnapshot == null) {
			this.initialSnapshot = new BoneSnapshot(this, true);
		}
	}

	@Override
	public BoneSnapshot getInitialSnapshot() {
		return this.initialSnapshot;
	}

	@Override
	public String getName() {
		return this.name;
	}

	// Boilerplate code incoming

	@Override
	public float getRotationX() {
		return rotateX;
	}

	@Override
	public float getRotationY() {
		return rotateY;
	}

	@Override
	public float getRotationZ() {
		return rotateZ;
	}

	@Override
	public float getPositionX() {
		return positionX;
	}

	@Override
	public float getPositionY() {
		return positionY;
	}

	@Override
	public float getPositionZ() {
		return positionZ;
	}

	@Override
	public float getScaleX() {
		return scaleX;
	}

	@Override
	public float getScaleY() {
		return scaleY;
	}

	@Override
	public float getScaleZ() {
		return scaleZ;
	}

	@Override
	public void setRotationX(float value) {
		this.rotateX = value;
	}

	@Override
	public void setRotationY(float value) {
		this.rotateY = value;
	}

	@Override
	public void setRotationZ(float value) {
		this.rotateZ = value;
	}

	@Override
	public void setPositionX(float value) {
		this.positionX = value;
	}

	@Override
	public void setPositionY(float value) {
		this.positionY = value;
	}

	@Override
	public void setPositionZ(float value) {
		this.positionZ = value;
	}

	@Override
	public void setScaleX(float value) {
		this.scaleX = value;
	}

	@Override
	public void setScaleY(float value) {
		this.scaleY = value;
	}

	@Override
	public void setScaleZ(float value) {
		this.scaleZ = value;
	}

	@Override
	public boolean isHidden() {
		return this.isHidden;
	}

	@Override
	public void setHidden(boolean hidden) {
		this.isHidden = hidden;
	}

	@Override
	public void setPivotX(float value) {
		this.rotationPointX = value;
	}

	@Override
	public void setPivotY(float value) {
		this.rotationPointY = value;
	}

	@Override
	public void setPivotZ(float value) {
		this.rotationPointZ = value;
	}

	@Override
	public float getPivotX() {
		return this.rotationPointX;
	}

	@Override
	public float getPivotY() {
		return this.rotationPointY;
	}

	@Override
	public float getPivotZ() {
		return this.rotationPointZ;
	}

	/**
	 * Performs a recursive copy of this bone.
	 * @param parent The cloned parent of this bone, or null if this bone is a top level bone.
	 * @return A copy of this bone.
	 */
	public GeoBone copy(@Nullable GeoBone parent) {
		GeoBone out = new GeoBone();
		out.parent = parent;
		// Assuming nothing ever mutates the child cubes, we can re-use the same list.
		out.childCubes = this.childCubes;

		out.scaleX = this.scaleX;
		out.scaleY = this.scaleY;
		out.scaleZ = this.scaleZ;
		out.positionX = this.positionX;
		out.positionY = this.positionY;
		out.positionZ = this.positionZ;
		out.rotationPointX = this.rotationPointX;
		out.rotationPointY = this.rotationPointY;
		out.rotationPointZ = this.rotationPointZ;
		out.rotateX = this.rotateX;
		out.rotateY = this.rotateY;
		out.rotateZ = this.rotateZ;

		out.name = this.name;
		out.initialSnapshot = this.initialSnapshot;
		out.mirror = this.mirror;
		out.inflate = this.inflate;
		out.dontRender = this.dontRender;
		out.isHidden = this.isHidden;
		out.reset = this.reset;

		for (GeoBone childBone : this.childBones) {
			out.childBones.add(childBone.copy(out));
		}

		return out;
	}
}
