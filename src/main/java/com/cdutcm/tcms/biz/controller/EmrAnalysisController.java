package com.cdutcm.tcms.biz.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.cdutcm.core.message.SysMsg;
import com.cdutcm.core.util.ClassUtil;
import com.cdutcm.core.util.IdWorker;
import com.cdutcm.core.util.JAXBUtil;
import com.cdutcm.core.util.StringUtil;
import com.cdutcm.tcms.biz.mapper.BaseRecipelItemMapper;
import com.cdutcm.tcms.biz.mapper.ValidationMapper;
import com.cdutcm.tcms.biz.model.BaseRecipelItem;
import com.cdutcm.tcms.biz.model.Emr;
import com.cdutcm.tcms.biz.model.IllnessHistory;
import com.cdutcm.tcms.biz.model.KbMedicinalMaterial;
import com.cdutcm.tcms.biz.model.Material;
import com.cdutcm.tcms.biz.model.Patient;
import com.cdutcm.tcms.biz.model.PatientRegist;
import com.cdutcm.tcms.biz.model.Recipel;
import com.cdutcm.tcms.biz.model.RecipelItem;
import com.cdutcm.tcms.biz.model.TcmspLog;
import com.cdutcm.tcms.biz.model.Validation;
import com.cdutcm.tcms.biz.service.EmrService;
import com.cdutcm.tcms.biz.service.KbDiseaseService;
import com.cdutcm.tcms.biz.service.KbMedicinalMaterialService;
import com.cdutcm.tcms.biz.service.MaterialService;
import com.cdutcm.tcms.biz.service.MedicineEighteenNinteenService;
import com.cdutcm.tcms.biz.service.TcmspLogService;
import com.cdutcm.tcms.biz.xml.BzVo;
import com.cdutcm.tcms.biz.xml.BzsVo;
import com.cdutcm.tcms.biz.xml.CfRowVo;
import com.cdutcm.tcms.biz.xml.CfVo;
import com.cdutcm.tcms.biz.xml.CfmxsVo;
import com.cdutcm.tcms.biz.xml.EMRVo;
import com.cdutcm.tcms.biz.xml.InfoVo;
import com.cdutcm.tcms.biz.xml.MxVo;
import com.cdutcm.tcms.biz.xml.PatientCardVo;
import com.cdutcm.tcms.biz.xml.PatientVo;
import com.cdutcm.tcms.biz.xml.RegistVo;
import com.cdutcm.tcms.redis.service.IRedisService;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * xml???json??????
 * 
 * @author fw
 * 
 */
@RestController
@RequestMapping("/analysis")
public class EmrAnalysisController {

	@Autowired
	private EmrService emrService;
	@Autowired
	private IRedisService redisService;
	@Autowired
	private KbMedicinalMaterialService kbMedicinalMaterialService;
	@Autowired
	private TcmspLogService tcmspLogService;
	@Autowired
	private KbDiseaseService kbDiseaseService;
	@Autowired
	private MedicineEighteenNinteenService medicineEighteenNinteenService;
	@Autowired
	private  MaterialService materialService;
	@Autowired
	private ValidationMapper validationMapper;
	
	private final static Logger logger = LoggerFactory
			.getLogger(EmrAnalysisController.class);

	private static final Logger errorlogger = LoggerFactory.getLogger("error");

	/**
	 * ??????his?????????xml?????????emr??????
	 * 
	 * @param xml
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "/xmlorjson", method = RequestMethod.POST)
	public Emr xmlToEmr(HttpServletRequest request) throws IOException {
		return this.jsonOrXmlToEmr(request);
	}

	/**
	 * emr??????json???????????????????????????emrVo Xml????????????emrVo Json?????????his
	 * 
	 * @param request
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "/parseemr")
	public String parseEmr(HttpServletRequest request) throws IOException {
		String data = "";
		EMRVo emrvo = this.emrToEMRVO(request);
		PatientVo patientVo = emrvo.getPatient();
		if (patientVo != null) {
			InfoVo info = patientVo.getInfo();
			if (info != null) {
				if (StringUtil.notEmpty(info.getSymptom())) {
					info.setSymptom(info.getSymptom().replaceAll("???", ","));
				}
			}
		}
		if ("XML".equals(emrvo.getDataType())) {
			JAXBUtil<EMRVo> jx = new JAXBUtil<EMRVo>();
			data = jx.JAXBmarshal(emrvo, "utf-8");
		} else {
			data = new ObjectMapper().writeValueAsString(emrvo);
		}
		logger.info("?????????his?????????:??????IP??????" + request.getRemoteAddr() + "?????????" + data
				+ '???');
		return data;
	}


	/**
	 * emr??????????????????????????????json?????????????????????
	 * 
	 * @param request
	 * @return
	 * @throws IOException
	 */
	public EMRVo emrToEMRVO(HttpServletRequest request) throws IOException {
		String body = null;
		StringBuilder stringBuilder = new StringBuilder();
		BufferedReader bufferedReader = null;
		try {
			InputStream inputStream = request.getInputStream();
			if (inputStream != null) {
				bufferedReader = new BufferedReader(new InputStreamReader(
						inputStream, "utf-8"));
				char[] charBuffer = new char[128];
				int bytesRead = -1;
				while ((bytesRead = bufferedReader.read(charBuffer)) > 0) {
					stringBuilder.append(charBuffer, 0, bytesRead);
				}
			} else {
				stringBuilder.append("");
			}
		} catch (IOException ex) {
			throw ex;
		} finally {
			if (bufferedReader != null) {
				try {
					bufferedReader.close();
				} catch (IOException ex) {
					throw ex;
				}
			}
		}
		body = stringBuilder.toString();
		// body =
		// "<EMR>    <patient>        <regist ys=\"???????????????\" ysbm=\"9999\" ghks=\"?????????\" ghksbm=\"C1\" kssj=\"2017-08-23 09:15:16\" patient_no=\"122550\" visit_no=\"0\" io=\"1\"></regist>        <patient_card patient_no=\"122550\" name=\"??????\" sex=\"???\" birthday=\"1977-07-18\" address=\"\" telephone=\"\" card_no=\"\" note=\"\" ye=\"0\"></patient_card>        <info>            <zs></zs>            <jws></jws>            <xbs></xbs>            <gms></gms>            <jzs></jzs>            <yjs></yjs>            <hys></hys>            <grs></grs>            <zz></zz>            <bzs>            </bzs>            <bw></bw>            <bx></bx>            <tz></tz>        </info>    </patient>     <cf>    </cf></EMR>";
		// body="{\"patient\":{\"id\":null,\"patientNo\":\"00014348\",\"name\":\"???**34\",\"sex\":\"???\",\"birthday\":\"20121204000000\",\"address\":\"\",\"telephone\":\"\",\"cardNo\":\"\",\"note\":\"\",\"ye\":0},\"patientRegist\":{\"id\":null,\"ys\":\"?????????\",\"ysbm\":\"bd0ac70d-52fc-4edc-a66a-e431dec3052e\",\"ghks\":\"????????????\",\"ghksbm\":\"10DDC185-BE10-447F-8C37-5C499E04C961\",\"patientNo\":\"00014348\",\"visitNo\":\"8b7c3d09-98fe-4a6a-9136-f12fb1dccfbc\",\"kssj\":\"20170724102709\",\"io\":\"1\"},\"symptom\":\"????????????,????????????,????????????,??????,??????,??????,??????,????????????,?????????,??????,????????????,????????????,??????,?????????,?????????,??????,??????,?????????,????????????,??????\",\"symptommould\":\"???????????????\",\"disease\":\"?????????\",\"illnessHistory\":{\"id\":null,\"chiefComplaint\":\"???\",\"presentIllness\":\"\",\"passedIllness\":\"\",\"personalIllness\":\"\",\"marriageHistory\":\"\",\"allergicHistory\":\"\",\"familyHistory\":\"\",\"menstruationHistory\":\"\",\"page\":null},\"bw\":\"???????????????\",\"bx\":\"????????????????????????\",\"chiefComplaint\":\"???\",\"recipels\":[{\"id\":null,\"name\":\"?????????\",\"therapy\":\"\",\"emrId\":null,\"doctorId\":\"\",\"lastupdate\":\"\",\"source\":\"\",\"fsCode\":\"4902416136202023001014600202520490501284033312\",\"weight\":\"\",\"version\":\"\",\"count\":60,\"recipelItems\":[{\"id\":\"32890485085437952\",\"recipelId\":32890485060272130,\"mOrder\":1,\"name\":\"??????\",\"pinyin\":\"\",\"dosage\":\"15\",\"unit\":\"\",\"countId\":\"\",\"hisId\":\"E82E967C-1F45-4357-9C9D-1E123EF02FF6\"},{\"id\":\"32890485089632256\",\"recipelId\":32890485060272130,\"mOrder\":2,\"name\":\"??????\",\"pinyin\":\"\",\"dosage\":\"15\",\"unit\":\"\",\"countId\":\"\",\"hisId\":\"959D1AE5-9387-4ED9-9977-2BE60E2265EC\"},{\"id\":\"32890485089632257\",\"recipelId\":32890485060272130,\"mOrder\":3,\"name\":\"??????\",\"pinyin\":\"\",\"dosage\":\"15\",\"unit\":\"\",\"countId\":\"\",\"hisId\":\"2C88B6CA-A08A-4DB9-BF3E-DE7AE89D227E\"},{\"id\":\"32890485089632258\",\"recipelId\":32890485060272130,\"mOrder\":4,\"name\":\"??????\",\"pinyin\":\"\",\"dosage\":\"30\",\"unit\":\"\",\"countId\":\"\",\"hisId\":\"A54C9F6B-9712-4970-ABC9-3051003CD30A\"},{\"id\":\"32890485093826560\",\"recipelId\":32890485060272130,\"mOrder\":5,\"name\":\"??????\",\"pinyin\":\"\",\"dosage\":\"20\",\"unit\":\"\",\"countId\":\"\",\"hisId\":\"1CF4A66A-7307-4F79-878E-76CD1B3890BC\"},{\"id\":\"32890485093826561\",\"recipelId\":32890485060272130,\"mOrder\":6,\"name\":\"??????\",\"pinyin\":\"\",\"dosage\":\"15\",\"unit\":\"\",\"countId\":\"\",\"hisId\":\"34C70382-C0CC-423A-B1A6-B2A3D40EBC5C\"},{\"id\":\"32890485093826562\",\"recipelId\":32890485060272130,\"mOrder\":7,\"name\":\"??????\",\"pinyin\":\"\",\"dosage\":\"20\",\"unit\":\"\",\"countId\":\"\",\"hisId\":\"88B0474A-C291-4361-9F3B-2CC95E03E72B\"},{\"id\":\"32890485110603776\",\"recipelId\":32890485060272130,\"mOrder\":8,\"name\":\"??????\",\"pinyin\":\"\",\"dosage\":\"20\",\"unit\":\"\",\"countId\":\"\",\"hisId\":\"87B23C0E-AD23-475B-BD55-F8244771E9F2\"},{\"id\":\"32890485118992384\",\"recipelId\":32890485060272130,\"mOrder\":9,\"name\":\"?????????\",\"pinyin\":\"\",\"dosage\":\"30\",\"unit\":\"\",\"countId\":\"\",\"hisId\":\"204105CC-6B30-45C8-88F0-3C1DF87317BD\"},{\"id\":\"32890485123186688\",\"recipelId\":32890485060272130,\"mOrder\":10,\"name\":\"??????\",\"pinyin\":\"\",\"dosage\":\"30\",\"unit\":\"\",\"countId\":\"\",\"hisId\":\"6B54443C-8265-4190-AA8E-75358D808DCB\"},{\"id\":\"32890485127380992\",\"recipelId\":32890485060272130,\"mOrder\":11,\"name\":\"??????\",\"pinyin\":\"\",\"dosage\":\"20\",\"unit\":\"\",\"countId\":\"\",\"hisId\":\"8BF02A20-D390-4A5A-9237-55128A612FB4\"},{\"id\":\"32890485127380993\",\"recipelId\":32890485060272130,\"mOrder\":12,\"name\":\"?????????\",\"pinyin\":\"\",\"dosage\":\"20\",\"unit\":\"\",\"countId\":\"\",\"hisId\":\"4051AD25-3105-43C4-B16A-568D3C7705B1\"},{\"id\":\"32890485127380994\",\"recipelId\":32890485060272130,\"mOrder\":13,\"name\":\"??????\",\"pinyin\":\"\",\"dosage\":\"12\",\"unit\":\"\",\"countId\":\"\",\"hisId\":\"E18CC02C-54FF-491B-8EA6-0538A9887EF9\"},{\"id\":\"32890485131575296\",\"recipelId\":32890485060272130,\"mOrder\":14,\"name\":\"?????????\",\"pinyin\":\"\",\"dosage\":\"20\",\"unit\":\"\",\"countId\":\"\",\"hisId\":\"B6D9CFC4-5C8C-4586-BE87-E3D2D3DA6F08\"},{\"id\":\"32890485131575297\",\"recipelId\":32890485060272130,\"mOrder\":15,\"name\":\"?????????\",\"pinyin\":\"\",\"dosage\":\"10\",\"unit\":\"\",\"countId\":\"\",\"hisId\":\"ff4c92d1-2442-4246-acad-dc76f8468d9c\"}],\"type\":\"recommend\",\"checked\":true}],\"fsCode\":\"6529513820003001002000806544010040301040141000\"}";
		// Emr emr = new Gson().fromJson(body, Emr.class);
		Emr emr = new ObjectMapper().readValue(body, Emr.class);
		// ???????????????????????????????????????his???????????????KbDisease????????????emr
		emr = emrService.findKbDiseaseByDisease(emr);
		EMRVo emrvo = new EMRVo();
		if (emr == null) {
			return emrvo;
		}
		PatientVo patient = new PatientVo();
		RegistVo regist = new RegistVo();
		PatientCardVo p = new PatientCardVo();
		BzsVo bzs = new BzsVo();
		List<BzVo> bz = new ArrayList<BzVo>();
		InfoVo info = new InfoVo();
		CfVo cf = new CfVo();
		List<CfRowVo> cfrows = new ArrayList<CfRowVo>();
		try {
			ClassUtil.copyObject(emr.getPatientRegist(), regist);
			ClassUtil.copyObject(emr.getPatient(), p);
			if (emr.getIllnessHistory() != null) {
				ClassUtil.copyObject(emr.getIllnessHistory(), info);
			} else {
				ClassUtil.copyObject(new IllnessHistory(), info);
			}
			ClassUtil.copyObject(emr, info);
			info.setSymptom(info.getSymptom().replaceAll("???", ","));
			patient.setPatientCard(p);
			patient.setRegist(regist);
			String disease = emr.getDisease();
			String symptommould = emr.getSymptommould();
			// ??????redis?????????????????????
			List<BzVo> redisBz = redisService.getList("Bzs", BzVo.class);
			if (!StringUtil.isEmpty(disease)) {
				List<String> diseases = Arrays.asList(disease.split(","));
				int i = 0;
				for (String d : diseases) {
					BzVo b = new BzVo();
					i++;
					// ?????????redis?????????????????????????????????
					if (!StringUtil.isEmptyList(redisBz)) {
						for (BzVo rb : redisBz) {
							if (d.equals(rb.getZdmc())) {
								b.setZdbm(rb.getZdbm());
								b.setZdlb(rb.getZdlb());
								b.setZdmc(rb.getZdmc());
								// b.setZdxtph(rb.getZdxtph());
								// ??????temp2?????????????????????id
								Integer zdbm = kbDiseaseService
										.findZdbmByZdmc(d);
								b.setZdxtph(zdbm + "");
								b.setXh(String.valueOf(i));
							} else {
								b.setXh(String.valueOf(i));
								b.setZdmc(d);
								// b.setZdxtph(rb.getZdxtph());
								// ??????temp2?????????????????????id
								Integer zdbm = kbDiseaseService
										.findZdbmByZdmc(d);
								b.setZdxtph(zdbm + "");
							}
						}
					} else {
						b.setXh(String.valueOf(i));
						b.setZdmc(d);
						Integer zdbm = kbDiseaseService.findZdbmByZdmc(d);
						b.setZdxtph(zdbm + "");
					}
					if (!StringUtil.isEmpty(symptommould)) {
						List<String> symptommoulds = Arrays.asList(symptommould
								.split(","));
						if (i <= symptommoulds.size()) {
							// ?????????redis?????????????????????????????????
							if (!StringUtil.isEmptyList(redisBz)) {
								for (BzVo rb : redisBz) {
									if (symptommoulds.contains(rb.getZxmc())) {
										b.setZxbm(rb.getZxbm());
										b.setZxmc(symptommoulds.get(i - 1));
										b.setZxxtph(rb.getZxxtph());
									} else {
										b.setZxmc(symptommoulds.get(i - 1));
									}
								}
							} else {
								b.setZxmc(symptommoulds.get(i - 1));
							}
						}
					}
					bz.add(b);
				}
			} else {
				logger.info("???{???????????????}???");
//				System.out.println("???????????????");
			}
			bzs.setBz(bz);
			info.setBzs(bzs);
			patient.setInfo(info);
			emrvo.setPatient(patient);
			//
			if (!StringUtil.isEmptyList(emr.getRecipels())) {
				for (Recipel recipel : emr.getRecipels()) {
					CfRowVo cfrow = new CfRowVo();
					CfmxsVo cfmxs = new CfmxsVo();
					ClassUtil.copyObject(recipel, cfrow);
					List<MxVo> mxs = new ArrayList<MxVo>();
					for (RecipelItem item : recipel.getRecipelItems()) {
						MxVo mx = new MxVo();
						ClassUtil.copyObject(item, mx);
						if (!StringUtil.isEmpty(item.getUsage())) {
							mx.setUsageName(item.getUsage());
						}
						mxs.add(mx);
					}
					cfmxs.setMx(mxs);
					cfrow.setCfmxs(cfmxs);
					cfrows.add(cfrow);
				}
			} else {
//				System.out.println("??????????????????!");
				logger.info("???{?????????????????????}???");
			}
			cf.setCfrow(cfrows);
			emrvo.setCf(cf);
			emrvo.setDataType(emr.getDataType());
		} catch (Exception e) {
			errorlogger.error(e.toString());
			throw new RuntimeException(e.toString());
		}
		return emrvo;
	}
	/**
	 * ?????????????????????(???????????????18???19???)
	 * @param request
	 * @return
	 * @throws IOException 
	 */
   @RequestMapping(value="/emrrule")
	public SysMsg emrInfoRule(HttpServletRequest request) throws IOException{
		String body = null;
		StringBuilder stringBuilder = new StringBuilder();
		BufferedReader bufferedReader = null;
		try {
			InputStream inputStream = request.getInputStream();
			if (inputStream != null) {
				bufferedReader = new BufferedReader(new InputStreamReader(
						inputStream, "utf-8"));
				char[] charBuffer = new char[128];
				int bytesRead = -1;
				while ((bytesRead = bufferedReader.read(charBuffer)) > 0) {
					stringBuilder.append(charBuffer, 0, bytesRead);
				}
			} else {
				stringBuilder.append("");
			}
		} catch (IOException e5) {
			throw e5;
		} finally {
			if (bufferedReader != null) {
				try {
					bufferedReader.close();
				} catch (IOException e1) {
					throw e1;
				}
			}
		}
		body = stringBuilder.toString();
		// body =
		// "<EMR>    <patient>        <regist ys=\"???????????????\" ysbm=\"9999\" ghks=\"?????????\" ghksbm=\"C1\" kssj=\"2017-08-23 09:15:16\" patient_no=\"122550\" visit_no=\"0\" io=\"1\"></regist>        <patient_card patient_no=\"122550\" name=\"??????\" sex=\"???\" birthday=\"1977-07-18\" address=\"\" telephone=\"\" card_no=\"\" note=\"\" ye=\"0\"></patient_card>        <info>            <zs></zs>            <jws></jws>            <xbs></xbs>            <gms></gms>            <jzs></jzs>            <yjs></yjs>            <hys></hys>            <grs></grs>            <zz></zz>            <bzs>            </bzs>            <bw></bw>            <bx></bx>            <tz></tz>        </info>    </patient>     <cf>    </cf></EMR>";
		// body="{\"patient\":{\"id\":null,\"patientNo\":\"00014348\",\"name\":\"???**34\",\"sex\":\"???\",\"birthday\":\"20121204000000\",\"address\":\"\",\"telephone\":\"\",\"cardNo\":\"\",\"note\":\"\",\"ye\":0},\"patientRegist\":{\"id\":null,\"ys\":\"?????????\",\"ysbm\":\"bd0ac70d-52fc-4edc-a66a-e431dec3052e\",\"ghks\":\"????????????\",\"ghksbm\":\"10DDC185-BE10-447F-8C37-5C499E04C961\",\"patientNo\":\"00014348\",\"visitNo\":\"8b7c3d09-98fe-4a6a-9136-f12fb1dccfbc\",\"kssj\":\"20170724102709\",\"io\":\"1\"},\"symptom\":\"????????????,????????????,????????????,??????,??????,??????,??????,????????????,?????????,??????,????????????,????????????,??????,?????????,?????????,??????,??????,?????????,????????????,??????\",\"symptommould\":\"???????????????\",\"disease\":\"?????????\",\"illnessHistory\":{\"id\":null,\"chiefComplaint\":\"???\",\"presentIllness\":\"\",\"passedIllness\":\"\",\"personalIllness\":\"\",\"marriageHistory\":\"\",\"allergicHistory\":\"\",\"familyHistory\":\"\",\"menstruationHistory\":\"\",\"page\":null},\"bw\":\"???????????????\",\"bx\":\"????????????????????????\",\"chiefComplaint\":\"???\",\"recipels\":[{\"id\":null,\"name\":\"?????????\",\"therapy\":\"\",\"emrId\":null,\"doctorId\":\"\",\"lastupdate\":\"\",\"source\":\"\",\"fsCode\":\"4902416136202023001014600202520490501284033312\",\"weight\":\"\",\"version\":\"\",\"count\":60,\"recipelItems\":[{\"id\":\"32890485085437952\",\"recipelId\":32890485060272130,\"mOrder\":1,\"name\":\"??????\",\"pinyin\":\"\",\"dosage\":\"15\",\"unit\":\"\",\"countId\":\"\",\"hisId\":\"E82E967C-1F45-4357-9C9D-1E123EF02FF6\"},{\"id\":\"32890485089632256\",\"recipelId\":32890485060272130,\"mOrder\":2,\"name\":\"??????\",\"pinyin\":\"\",\"dosage\":\"15\",\"unit\":\"\",\"countId\":\"\",\"hisId\":\"959D1AE5-9387-4ED9-9977-2BE60E2265EC\"},{\"id\":\"32890485089632257\",\"recipelId\":32890485060272130,\"mOrder\":3,\"name\":\"??????\",\"pinyin\":\"\",\"dosage\":\"15\",\"unit\":\"\",\"countId\":\"\",\"hisId\":\"2C88B6CA-A08A-4DB9-BF3E-DE7AE89D227E\"},{\"id\":\"32890485089632258\",\"recipelId\":32890485060272130,\"mOrder\":4,\"name\":\"??????\",\"pinyin\":\"\",\"dosage\":\"30\",\"unit\":\"\",\"countId\":\"\",\"hisId\":\"A54C9F6B-9712-4970-ABC9-3051003CD30A\"},{\"id\":\"32890485093826560\",\"recipelId\":32890485060272130,\"mOrder\":5,\"name\":\"??????\",\"pinyin\":\"\",\"dosage\":\"20\",\"unit\":\"\",\"countId\":\"\",\"hisId\":\"1CF4A66A-7307-4F79-878E-76CD1B3890BC\"},{\"id\":\"32890485093826561\",\"recipelId\":32890485060272130,\"mOrder\":6,\"name\":\"??????\",\"pinyin\":\"\",\"dosage\":\"15\",\"unit\":\"\",\"countId\":\"\",\"hisId\":\"34C70382-C0CC-423A-B1A6-B2A3D40EBC5C\"},{\"id\":\"32890485093826562\",\"recipelId\":32890485060272130,\"mOrder\":7,\"name\":\"??????\",\"pinyin\":\"\",\"dosage\":\"20\",\"unit\":\"\",\"countId\":\"\",\"hisId\":\"88B0474A-C291-4361-9F3B-2CC95E03E72B\"},{\"id\":\"32890485110603776\",\"recipelId\":32890485060272130,\"mOrder\":8,\"name\":\"??????\",\"pinyin\":\"\",\"dosage\":\"20\",\"unit\":\"\",\"countId\":\"\",\"hisId\":\"87B23C0E-AD23-475B-BD55-F8244771E9F2\"},{\"id\":\"32890485118992384\",\"recipelId\":32890485060272130,\"mOrder\":9,\"name\":\"?????????\",\"pinyin\":\"\",\"dosage\":\"30\",\"unit\":\"\",\"countId\":\"\",\"hisId\":\"204105CC-6B30-45C8-88F0-3C1DF87317BD\"},{\"id\":\"32890485123186688\",\"recipelId\":32890485060272130,\"mOrder\":10,\"name\":\"??????\",\"pinyin\":\"\",\"dosage\":\"30\",\"unit\":\"\",\"countId\":\"\",\"hisId\":\"6B54443C-8265-4190-AA8E-75358D808DCB\"},{\"id\":\"32890485127380992\",\"recipelId\":32890485060272130,\"mOrder\":11,\"name\":\"??????\",\"pinyin\":\"\",\"dosage\":\"20\",\"unit\":\"\",\"countId\":\"\",\"hisId\":\"8BF02A20-D390-4A5A-9237-55128A612FB4\"},{\"id\":\"32890485127380993\",\"recipelId\":32890485060272130,\"mOrder\":12,\"name\":\"?????????\",\"pinyin\":\"\",\"dosage\":\"20\",\"unit\":\"\",\"countId\":\"\",\"hisId\":\"4051AD25-3105-43C4-B16A-568D3C7705B1\"},{\"id\":\"32890485127380994\",\"recipelId\":32890485060272130,\"mOrder\":13,\"name\":\"??????\",\"pinyin\":\"\",\"dosage\":\"12\",\"unit\":\"\",\"countId\":\"\",\"hisId\":\"E18CC02C-54FF-491B-8EA6-0538A9887EF9\"},{\"id\":\"32890485131575296\",\"recipelId\":32890485060272130,\"mOrder\":14,\"name\":\"?????????\",\"pinyin\":\"\",\"dosage\":\"20\",\"unit\":\"\",\"countId\":\"\",\"hisId\":\"B6D9CFC4-5C8C-4586-BE87-E3D2D3DA6F08\"},{\"id\":\"32890485131575297\",\"recipelId\":32890485060272130,\"mOrder\":15,\"name\":\"?????????\",\"pinyin\":\"\",\"dosage\":\"10\",\"unit\":\"\",\"countId\":\"\",\"hisId\":\"ff4c92d1-2442-4246-acad-dc76f8468d9c\"}],\"type\":\"recommend\",\"checked\":true}],\"fsCode\":\"6529513820003001002000806544010040301040141000\"}";
		// Emr emr = new Gson().fromJson(body, Emr.class);
		// ??????recipel??????
	   Emr emr = new ObjectMapper().readValue(body, Emr.class);
	   SysMsg msg = new SysMsg();
	   msg.setStatus("TS");
	   String cantent = "";
	   try {
		int i =this.StarRating(emr);
		String d = materialService.drugRule(emr);
		List<String> s= this.EighteenNinteen(emr);
		cantent="{emrstar:"+i+",eighteenninteen:"+s+",drug:"+d+"}";
		msg.setContent(cantent);
	  } catch (IOException e) {
		// TODO Auto-generated catch block
		msg.setStatus("FS");
		e.printStackTrace();
	 }
	   return msg;
	}
	/**
	 * ????????????his??????xml??????json???????????????emr
	 * 
	 * @param jsonOrxml
	 * @return
	 */
	public Emr jsonOrXmlToEmr(HttpServletRequest request) throws IOException {
		// String jsonOrxml
		// ="{\"patient\":{\"regist\":{\"ys\":\"?????????\",\"ysbm\":\"bd0ac70d-52fc-4edc-a66a-e431dec3052e\",\"ghks\":\"????????????\",\"ghksbm\":\"10DDC185-BE10-447F-8C37-5C499E04C961\",\"patient_no\":\"00013995\",\"visit_no\":\"5d734498-beca-40d3-995c-af8193610ef6\",\"kssj\":\"20170704160840\",\"io\":\"0\"},\"patient_card\":{\"patient_no\":\"00013995\",\"name\":\"??????\",\"sex\":\"???\",\"birthday\":\"19800101000000\",\"address\":\"\",\"telephone\":\"\",\"card_no\":null,\"note\":\"\",\"ye\":\"0\"},\"info\":{\"zs\":\"?????????????????????????????????????????????????????????\",\"jws\":\"????????????????????????????????????????????????????????????\",\"xbs\":\"??????3+?????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????1+????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????\",\"gms\":\"??????\",\"jzs\":\"\",\"yjs\":\"\",\"hys\":\"\",\"grs\":\"\",\"zz\":\"\",\"bw\":\"\",\"bx\":\"\",\"tz\":\"\",\"bzs\":{\"bz\":[{\"xh\":\"1\",\"zdmc\":\"?????????\",\"zdbm\":\"BNS150\",\"zdlb\":\"1\",\"zxmc\":\"\",\"zxbm\":\"\"}]}}},\"cf\":{\"cfrow\":[{\"xh\":\"f567547e-ce0f-457d-9a2d-589427941af5\",\"mc\":\"???????????????\",\"zf\":\"\",\"lx\":\"2\",\"yf\":\"A30656FB-5E00-468A-9F49-EBF99123C1E7\",\"sm\":\"??????:???????????????,??????:???,??????1???,???????????????ML\",\"note\":\"??????:???????????????,??????:???,??????1???,???????????????ML\",\"cfmxs\":{\"mx\":[{\"xh\":\"6\",\"xtph\":\"43ED6262-CF53-4DC4-B274-CDD44FC24259\",\"mc\":\"??????\",\"jl\":\"15\",\"dw\":\"???\",\"gg\":\"\",\"yf\":\"\",\"jczs\":\"\"},{\"xh\":\"1\",\"xtph\":\"17809039-A09D-45F3-93D1-A2C861FE5C23\",\"mc\":\"??????\",\"jl\":\"30\",\"dw\":\"???\",\"gg\":\"\",\"yf\":\"\",\"jczs\":\"\"},{\"xh\":\"4\",\"xtph\":\"8FCE70A4-37E2-47FF-8B74-175E89207C8A\",\"mc\":\"??????\",\"jl\":\"15\",\"dw\":\"???\",\"gg\":\"\",\"yf\":\"\",\"jczs\":\"\"},{\"xh\":\"8\",\"xtph\":\"D2FB61B5-7C10-45E1-B487-6600B8EA2F46\",\"mc\":\"?????????\",\"jl\":\"6\",\"dw\":\"???\",\"gg\":\"\",\"yf\":\"\",\"jczs\":\"\"},{\"xh\":\"2\",\"xtph\":\"0db8168e-fe23-434f-95ab-574a726f9511\",\"mc\":\"??????\",\"jl\":\"15\",\"dw\":\"???\",\"gg\":\"\",\"yf\":\"\",\"jczs\":\"\"},{\"xh\":\"9\",\"xtph\":\"FDA6CF53-9BE6-4E88-BE5A-0658CFD7690E\",\"mc\":\"??????\",\"jl\":\"6\",\"dw\":\"???\",\"gg\":\"\",\"yf\":\"\",\"jczs\":\"\"},{\"xh\":\"7\",\"xtph\":\"4AD29706-BF86-40B4-8C96-489A7C6A6490\",\"mc\":\"??????\",\"jl\":\"15\",\"dw\":\"???\",\"gg\":\"\",\"yf\":\"\",\"jczs\":\"\"},{\"xh\":\"3\",\"xtph\":\"7EBEEDBD-2F30-4757-9348-4F869AA74D31\",\"mc\":\"??????\",\"jl\":\"15\",\"dw\":\"???\",\"gg\":\"\",\"yf\":\"\",\"jczs\":\"\"},{\"xh\":\"5\",\"xtph\":\"E82E967C-1F45-4357-9C9D-1E123EF02FF6\",\"mc\":\"??????\",\"jl\":\"15\",\"dw\":\"???\",\"gg\":\"\",\"yf\":\"\",\"jczs\":\"\"}]}}]}}";
		String jsonOrxml = null;
		StringBuilder stringBuilder = new StringBuilder();
		BufferedReader bufferedReader = null;
		try {
			InputStream inputStream = request.getInputStream();
			if (inputStream != null) {
				bufferedReader = new BufferedReader(new InputStreamReader(
						inputStream, "utf-8"));
				char[] charBuffer = new char[128];
				int bytesRead = -1;
				while ((bytesRead = bufferedReader.read(charBuffer)) > 0) {
					stringBuilder.append(charBuffer, 0, bytesRead);
				}
			} else {
				stringBuilder.append("");
			}
		} catch (IOException ex) {
			throw ex;
		} finally {
			if (bufferedReader != null) {
				try {
					bufferedReader.close();
				} catch (IOException ex) {
					throw ex;
				}
			}
		}
		jsonOrxml = stringBuilder.toString();
		logger.info("?????????HIS?????????????????????IP??????" + request.getRemoteAddr() + "?????????"
				+ jsonOrxml + '???');
		IdWorker idWorker = new IdWorker();
		TcmspLog tcmspLog = new TcmspLog();
		tcmspLog.setId(idWorker.nextId());
		tcmspLog.setLastupdate(new Date());
		tcmspLog.setDescription(jsonOrxml);
		Emr emr = new Emr();
		IllnessHistory illnessHistory = new IllnessHistory();
		Patient patient = new Patient();
		PatientRegist patientRegist = new PatientRegist();
		// InfoVo info = new InfoVo();
		BzsVo bzs = new BzsVo();
		List<Recipel> recipels = new ArrayList<Recipel>();
		try {
			EMRVo emrvo;
			EMRVo e = new EMRVo();
			if (StringUtil.isEmpty(jsonOrxml)) {
				return emr;
			}
			if (jsonOrxml.indexOf("</EMR>") >= 1) {
				tcmspLog.setName("??????his??????????????????xml");
				JAXBUtil<EMRVo> jx = new JAXBUtil<EMRVo>();
				emrvo = jx.JAXBunmarshal(jsonOrxml, e);
				PatientVo patientVo = emrvo.getPatient();
				if (patientVo != null) {
					InfoVo info = patientVo.getInfo();
					if (info != null) {
						if (StringUtil.notEmpty(info.getSymptom())) {
							info.setSymptom(info.getSymptom().replaceAll("???",
									","));
						}
					}
				}
				// if (StringUtil.isEmpty(redisService.get("parsemanner"))) {
				// redisService.set("parsemanner", "xml");
				emr.setDataType("XML");
				// }
			} else {
				tcmspLog.setName("??????his??????????????????json");
				try {
					emrvo = new ObjectMapper()
							.readValue(jsonOrxml, EMRVo.class);
				} catch (Exception e2) {
					throw new RuntimeException("?????????????????????" + e2.toString());
				}
				// if (StringUtil.isEmpty(redisService.get("parsemanner"))) {
				// redisService.set("parsemanner", "json");
				emr.setDataType("JSON");
				// }
			}
			// ?????????????????????redis
			if (emrvo.getPatient() != null
					&& emrvo.getPatient().getInfo() != null
					&& emrvo.getPatient().getInfo().getBzs() != null) {
				redisService.setList("Bzs", (List<BzVo>) emrvo.getPatient()
						.getInfo().getBzs().getBz());
			}
			// TODO Patient??????note??????
			ClassUtil.copyObject(emrvo.getPatient().getPatientCard(), patient);
			ClassUtil.copyObject(emrvo.getPatient().getRegist(), patientRegist);
			ClassUtil.copyObject(emrvo.getPatient().getInfo(), illnessHistory);
			ClassUtil.copyObject(emrvo.getPatient().getInfo(), emr);
			bzs = emrvo.getPatient().getInfo().getBzs();
			String disease = "";
			String symptommould = "";
			if (bzs != null && !StringUtil.isEmptyList(bzs.getBz())) {
				for (BzVo bz : bzs.getBz()) {
					// TODO zd????????????????????????
					if (StringUtil.notEmpty(bz.getZdmc())) {
						disease += bz.getZdmc() + ",";
					}
					// TODO zs????????????????????????
					if (StringUtil.notEmpty(bz.getZxmc())) {
						symptommould += bz.getZxmc() + ",";
					}
				}
			}
			emr.setIllnessHistory(illnessHistory);
			if (StringUtil.notEmpty(disease)) {
				if (disease.lastIndexOf(",") > 0) {
					emr.setDisease(disease.substring(0, disease.length() - 1));
				} else {
					emr.setDisease(disease);
				}
			}
			if (StringUtil.notEmpty(symptommould)) {
				if (symptommould.lastIndexOf(",") > 0) {
					emr.setSymptommould(symptommould.substring(0,
							symptommould.length() - 1));
				} else {
					emr.setSymptommould(symptommould);
				}
			}
			emr.setPatientRegist(patientRegist);
			emr.setPatient(patient);
			if (!StringUtil.objIsEmpty(emrvo.getCf())
					&& !StringUtil.isEmptyList((emrvo.getCf().getCfrow()))
					&& emrvo.getCf().getCfrow().get(0) != null) {
				for (CfRowVo lcfrow : emrvo.getCf().getCfrow()) {
					Recipel recipel = new Recipel();
					List<RecipelItem> recipelItem = new ArrayList<RecipelItem>();
					ClassUtil.copyObject(lcfrow, recipel);
					for (MxVo lmx : lcfrow.getCfmxs().getMx()) {
						RecipelItem r = new RecipelItem();
						ClassUtil.copyObject(lmx, r);
						recipelItem.add(r);
						recipel.setRecipelItems(recipelItem);
					}

					String recipelItems = "";
					for (int o = 0; o < recipel.getRecipelItems().size(); o++) {
						if ("".equals(recipelItem.get(o).getName())) {
							break;
						}
						recipelItems += recipelItem.get(o).getName() + ",";
					}
					List<KbMedicinalMaterial> ks = kbMedicinalMaterialService
							.findKbMedicinalMaterialByName(recipelItems);
					String code = "";
					for (int i = 0; i < 46; i++) {
						int ii = 0;
						for (int j = 0; j < ks.size(); j++) {
							if (StringUtil.notEmpty(ks.get(j).getFsCode())) {
								ii += Integer.parseInt(ks.get(j).getFsCode()
										.substring(i, i + 1));
							}
						}
						code += ii > 9 ? 9 : ii;
					}
					recipel.setFsCode(code);
					recipels.add(recipel);
				}
			}
			if (StringUtil.isEmpty(emr.getSymptom())) {
				emr.setSymptom("");
			}
			if (StringUtil.isEmpty(emr.getSymptommould())) {
				emr.setSymptommould("");
			}
			if (StringUtil.isEmpty(emr.getDisease())) {
				emr.setDisease("");
			}
			emr.setRecipels(recipels);
			// ??????????????????
			tcmspLog.setCreateUser(emrvo.getPatient().getRegist().getYs());
			logger.info("HIS?????????????????????????????????IP??????" + request.getRemoteAddr() + "?????????"
					+ emrvo.getPatient().getRegist().getVisitNo() + '???');
			// ??????his??????????????????????????????
			tcmspLogService.saveTcmspLog(tcmspLog);
		} catch (Exception e1) {
			errorlogger.error(e1.toString());
			e1.printStackTrace();
			throw new RuntimeException(e1.toString());
		}
		// AUTO
		emrService.sendEmr(emr);
		return emr;
	}
	/**
	 * 18???19???
	 * @param emr
	 * @return
	 * @throws IOException
	 */
  public  List<String> EighteenNinteen(Emr emr) throws IOException{
		// ??????recipel??????
		List<Recipel> recipels = emr.getRecipels();
		if (recipels.size() > 0) {
			if(recipels.get(0).getRecipelItems() !=null){
				if(recipels.get(0).getRecipelItems().size()>0){	
					// ???????????????????????????
				   List<String> msg = new ArrayList<String> ();
					for (Recipel recipel : recipels) {
						// ???????????????????????????????????????
						List<RecipelItem> items = recipel.getRecipelItems();
						int mOrder = 0;
						// ??????18???19????????????
						List<String> eightNineList = new ArrayList<String>();
						for (RecipelItem r : items) {
							r.setmOrder(mOrder);
							eightNineList.add(r.getName());
						}
						// ??????????????????
						List<String> eightNineMsg = medicineEighteenNinteenService
								.getDataByConflict(eightNineList);
						if (eightNineMsg.size() > 0) {
							StringBuffer s = new StringBuffer();
							for (String string : eightNineMsg) {
								s.append( string+"-");
							}						
							msg.add(s.substring(0,s.length() - 1).toString());
						}
					}
					return msg;
				}
			}
			}
		return  null;
		}
  	/**
  	 * 18???19??????????????????
   	*/
     
  public  List<String> EighteenNinteen1(Emr emr) throws IOException{
		// ??????recipel??????  
	    IdWorker idworker=new IdWorker();
		List<Recipel> recipels = emr.getRecipels();
		List<Validation> validations=new ArrayList<Validation>();
		if (recipels.size() > 0) {
			if(recipels.get(0).getRecipelItems() !=null){
				if(recipels.get(0).getRecipelItems().size()>0){	
					// ???????????????????????????
				   List<String> msg = new ArrayList<String> ();
					for (Recipel recipel : recipels) {
						if(!StringUtil.isEmptyList(recipel.getRecipelItems())){
						// ???????????????????????????????????????
						List<RecipelItem> items = recipel.getRecipelItems();
						
						int mOrder = 0;
						// ??????18???19????????????
						List<String> eightNineList = new ArrayList<String>();
						for (RecipelItem r : items) {
							r.setmOrder(mOrder);
							eightNineList.add(r.getName());
						}
						// ??????????????????
 						List<String> eightNineMsg = medicineEighteenNinteenService
                    								.getDataByConflict(eightNineList);
						if (eightNineMsg.size() > 0) {
							StringBuffer s = new StringBuffer();
							for (String string : eightNineMsg) {
								s.append( string+"-");
								Validation validation=new Validation();
								validation.setEmrid(emr.getId());
								validation.setKey("pw");
								//validation.setRecipelid(recipel.getId());
								validation.setId(idworker.nextId());
								validation.setValue(string);
								validations.add(validation);
							}						
							msg.add(s.substring(0,s.length() - 1).toString());
								
						}
					}
				}   
					if(!StringUtil.isEmptyList(validations)){
					validationMapper.insertValidation(validations);
					}
					return msg;
				}
			}
			}
		return  null;
		}
	/**
	 * ????????????
	 * @param request
	 * @return
	 * @throws IOException
	 */
	public int StarRating(Emr emr) throws IOException{
		int level=5;
		if(StringUtil.isEmptyList(emr.getRecipels())){
			level--;
		}else{
			if(StringUtil.isEmpty(emr.getRecipels().get(0).getTherapy())){
				level--;
			}
			if(StringUtil.isEmptyList(emr.getRecipels().get(0).getRecipelItems())){
				level--;
			}
		}
		if(StringUtil.isEmpty(emr.getDisease())){
			level--;
		}
		if(StringUtil.isEmpty(emr.getSymptom())){
			level--;
		}
		if(StringUtil.isEmpty(emr.getSymptommould())){
			level--;
		}
		return level;
	}
	

	public List<Integer> StarRating1(Emr emr){
		int level=5;
		List<Integer>  le=new ArrayList<Integer>();
		if(emr.getRecipels()!=null&&!StringUtil.isEmptyList(emr.getRecipels())){
		for (int i = 0; i < emr.getRecipels().size(); i++) {
			if(StringUtil.isEmptyList(emr.getRecipels())){
				level--;
				level--;
				
			}else{
				
				if(StringUtil.isEmpty(emr.getRecipels().get(i).getTherapy())){
					level--;
				}
				if(StringUtil.isEmptyList(emr.getRecipels().get(i).getRecipelItems())){
					level--;
				}
			}
			if(StringUtil.isEmpty(emr.getDisease())){
				level--;
			}
			if(StringUtil.isEmpty(emr.getSymptom())){
				level--;
			}
			if(StringUtil.isEmpty(emr.getSymptommould())){
				level--;
			}
			le.add(level);
		}
		}else{
			if(StringUtil.isEmpty(emr.getDisease())){
				level--;
			}
			if(StringUtil.isEmpty(emr.getSymptom())){
				level--;
			}
			if(StringUtil.isEmpty(emr.getSymptommould())){
				level--;
			}
			level--;
			level--;
			le.add(level);
		}
		return le;
	}
	/**
	 * ????????????
	 * @param emr
	 * @return
	 */
	public List<Integer> StarRating2(Emr emr){
		
		int level=5;
		List<Integer>  le=new ArrayList<Integer>();
		IdWorker idworker=new IdWorker();
		if(emr.getRecipels()!=null&&!StringUtil.isEmptyList(emr.getRecipels())){
		for (int i = 0; i < emr.getRecipels().size(); i++) {
			if(StringUtil.isEmptyList(emr.getRecipels())){
				level--;
				level--;
				
			}else{
				
				if(StringUtil.isEmpty(emr.getRecipels().get(i).getTherapy())){
					level--;
				}
				if(StringUtil.isEmptyList(emr.getRecipels().get(i).getRecipelItems())){
					level--;
				}
			}
			if(StringUtil.isEmpty(emr.getDisease())){
				level--;
			}
			if(StringUtil.isEmpty(emr.getSymptom())){
				level--;
			}
			if(StringUtil.isEmpty(emr.getSymptommould())){
				level--;
			}
			le.add(level);
			
		}
		
		}else{
			if(StringUtil.isEmpty(emr.getDisease())){
				level--;
			}
			if(StringUtil.isEmpty(emr.getSymptom())){
				level--;
			}
			if(StringUtil.isEmpty(emr.getSymptommould())){
				level--;
			}
			level--;
			level--;
			le.add(level);
		
		}
		List<Validation> validations=new ArrayList<Validation>();
		Validation validation=new Validation();
		validation.setEmrid(emr.getId());
		validation.setKey("star");
		validation.setId(idworker.nextId());
		validation.setValue(le.toString());
		validations.add(validation);
	
		validationMapper.insertValidation(validations);
		return le;
	
	
	}
	
	/**
	 * ??????????????????
	 * 
	 */
	
	public List<Integer> StarRating3(Emr emr){
		
		String level;
		List<Integer>  le=new ArrayList<Integer>();
		IdWorker idworker=new IdWorker();
		List<Validation> validations=new ArrayList<Validation>();
		if(isdiease(emr)&&ismedicinal(emr)==false){
			level="1";
		}else if(ismedicinal(emr)&&isdiease(emr)==false){
			level="1";
		}else if(isdiease(emr)&&ismedicinal(emr)&&issystom(emr)==false){
			level="2";
		}else if(isdiease(emr)&&ismedicinal(emr)&&issystom(emr)&&issystommould(emr)==false){
			level="3";
		}else if(isdiease(emr)&&ismedicinal(emr)&&issystom(emr)&&issystommould(emr)&&isbworbx(emr)==false){
			level="4";
		}else if(isdiease(emr)&&ismedicinal(emr)&&issystom(emr)&&issystommould(emr)&&isbworbx(emr)){
			level="5";
		}else{
			level="0";
		}
		if(!issystommould(emr)){
			Validation validation=new Validation();
			validation.setEmrid(emr.getId());
			validation.setKey("mould");
			validation.setId(idworker.nextId());
			validation.setValue("????????????");
			validations.add(validation);
		}
		
		Validation validation=new Validation();
		validation.setEmrid(emr.getId());
		validation.setKey("star");
		validation.setId(idworker.nextId());
		validation.setValue(level);
		validations.add(validation);
	
		validationMapper.insertValidation(validations);
		return le;
	
	
	}
	/**
	 * ??????????????????
	 */
	public boolean isdiease(Emr emr){
	if(StringUtil.isEmpty(emr.getDisease())){
		return false;
	}else{
		return true;
	}	
		}
	/**
	 * ??????????????????
	 *
	 */
	public boolean issystom(Emr emr){
		if(StringUtil.isEmpty(emr.getSymptom())){
			return false;
		}else{
			return true;
		}	
	}
	/**
	 * ??????????????????,??????????????????
	 *
	 */
	public boolean issystommould(Emr emr){
		if(!StringUtil.isEmpty(emr.getSymptommould())){
			return true;
		}
		if(!StringUtil.isEmptyList(emr.getRecipels())){
		for (int i = 0; i < emr.getRecipels().size(); i++) {
		    if(!StringUtil.isEmpty(emr.getRecipels().get(i).getTherapy())){
		    	return true;
		    }	
		}
		}
		return false;
		
	}
	/**
	 * ?????????????????????????????????
	 */
	public boolean isbworbx(Emr emr){
	if(StringUtil.isEmpty(emr.getBw())&&StringUtil.isEmpty(emr.getBx())){
		return false;
	}else{
		return true;
	}	
	}
	/**
	 * ??????????????????	
	 */
	public boolean ismedicinal(Emr emr){
	if(StringUtil.isEmptyList(emr.getRecipels())){
		return false;
	}
	for (int i = 0; i < emr.getRecipels().size(); i++) {
		if(!StringUtil.isEmptyList(emr.getRecipels().get(i).getRecipelItems())){
			return true;
		}
	}
		return false;

	}
	
	public boolean isdosage(Emr emr){
		List<RecipelItem> re=emr.getRecipelItems();
		if(StringUtil.isEmptyList(emr.getRecipelItems())){
			return false;
		}
		for(RecipelItem item:re){
			String name=item.getName();
			Material m=materialService.selectByName(name);
			//?????????????????????
			int max= Integer.parseInt(new java.text.DecimalFormat("0").format(m.getMaxDosage()));				
			int dosage=Integer.valueOf(item.getDosage()).intValue();//?????????????????????
			if(dosage>max){
				return false;
			}else{
				return true;
			}
		}
		return false;
			
		}
		
}
