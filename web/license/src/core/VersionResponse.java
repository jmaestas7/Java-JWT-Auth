package core;

public class VersionResponse {
	private String version = "";
	private String utcTS = "";
	public String getVersion() {
		return version;
	}
	public String getUtcTS() {
		return utcTS;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	public void setUtcTS(String utcTS) {
		this.utcTS = utcTS;
	}
}
