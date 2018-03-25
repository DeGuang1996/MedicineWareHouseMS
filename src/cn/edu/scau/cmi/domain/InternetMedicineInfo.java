package cn.edu.scau.cmi.domain;

import java.util.List;

/*中国兽医信息网药品信息
 * 网页抓包返回后封装
 * 
 * */

public class InternetMedicineInfo {
	List<Rows> rows;

	public List<Rows> getRows() {
		return rows;
	}

	public void setRows(List<Rows> rows) {
		this.rows = rows;
	}

	public class Rows {
		String chsf;
		String cpname;
		String cxcs;
		String dyccxsj;
		String jxname;
		String minpackunit;
		String mintagunit;
		String ph;
		String pzwh;
		String qyname;
		String scrq;
		String specification;
		String spm;
		String sxrq;
		String tagratio;

		@Override
		public String toString() {
			return "InternetMedicineInfo [chsf=" + chsf + ", cpname=" + cpname + ", cxcs=" + cxcs + ", dyccxsj="
					+ dyccxsj + ", jxname=" + jxname + ", minpackunit=" + minpackunit + ", mintagunit=" + mintagunit
					+ ", ph=" + ph + ", pzwh=" + pzwh + ", qyname=" + qyname + ", scrq=" + scrq + ", specification="
					+ specification + ", spm=" + spm + ", sxrq=" + sxrq + ", tagratio=" + tagratio + ", tmjb=" + tmjb
					+ ", yplxname=" + yplxname + ", zsmtype=" + zsmtype + "]";
		}

		String tmjb;
		String yplxname;
		String zsmtype;

		public String getChsf() {
			return chsf;
		}

		public void setChsf(String chsf) {
			this.chsf = chsf;
		}

		public String getCpname() {
			return cpname;
		}

		public void setCpname(String cpname) {
			this.cpname = cpname;
		}

		public String getCxcs() {
			return cxcs;
		}

		public void setCxcs(String cxcs) {
			this.cxcs = cxcs;
		}

		public String getDyccxsj() {
			return dyccxsj;
		}

		public void setDyccxsj(String dyccxsj) {
			this.dyccxsj = dyccxsj;
		}

		public String getJxname() {
			return jxname;
		}

		public void setJxname(String jxname) {
			this.jxname = jxname;
		}

		public String getMinpackunit() {
			return minpackunit;
		}

		public void setMinpackunit(String minpackunit) {
			this.minpackunit = minpackunit;
		}

		public String getMintagunit() {
			return mintagunit;
		}

		public void setMintagunit(String mintagunit) {
			this.mintagunit = mintagunit;
		}

		public String getPh() {
			return ph;
		}

		public void setPh(String ph) {
			this.ph = ph;
		}

		public String getPzwh() {
			return pzwh;
		}

		public void setPzwh(String pzwh) {
			this.pzwh = pzwh;
		}

		public String getQyname() {
			return qyname;
		}

		public void setQyname(String qyname) {
			this.qyname = qyname;
		}

		public String getScrq() {
			return scrq;
		}

		public void setScrq(String scrq) {
			this.scrq = scrq;
		}

		public String getSpecification() {
			return specification;
		}

		public void setSpecification(String specification) {
			this.specification = specification;
		}

		public String getSpm() {
			return spm;
		}

		public void setSpm(String spm) {
			this.spm = spm;
		}

		public String getSxrq() {
			return sxrq;
		}

		public void setSxrq(String sxrq) {
			this.sxrq = sxrq;
		}

		public String getTagratio() {
			return tagratio;
		}

		public void setTagratio(String tagratio) {
			this.tagratio = tagratio;
		}

		public String getTmjb() {
			return tmjb;
		}

		public void setTmjb(String tmjb) {
			this.tmjb = tmjb;
		}

		public String getYplxname() {
			return yplxname;
		}

		public void setYplxname(String yplxname) {
			this.yplxname = yplxname;
		}

		public String getZsmtype() {
			return zsmtype;
		}

		public void setZsmtype(String zsmtype) {
			this.zsmtype = zsmtype;
		}

	}
}
