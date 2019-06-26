package com.halfroom.distribution.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import com.halfroom.distribution.persistence.dao.CardMapper;
import com.halfroom.distribution.persistence.model.*;
import org.springframework.stereotype.Service;

import com.halfroom.distribution.core.util.QRCodeUtil;
import com.halfroom.distribution.core.util.ZipUtil;
import com.halfroom.distribution.dao.CardProductRecordDao;
import com.halfroom.distribution.dao.CardWholeSaleDao;
import com.halfroom.distribution.dao.GeneralUserDao;
import com.halfroom.distribution.persistence.vo.ShareBookUrlVo;
import com.halfroom.distribution.persistence.vo.ZipPictureEntryVo;
import com.halfroom.distribution.service.ICardRawService;
import com.halfroom.distribution.service.IZipService;

@Service
public class ZipServiceImpl implements IZipService {

	@Resource
	private GeneralUserDao generalUserDao;
	@Resource
	private CardProductRecordDao cardProductRecordDao;
	@Resource
	private ICardRawService icardRawService;
	@Resource
	private CardWholeSaleDao cardWholeSaleDao;
	@Resource
	private CardMapper cardMapper;

	@Override
	public int exportSalrQRcode(HttpServletResponse response, Integer userid) {
		User user = generalUserDao.selectByid(userid);
		if (user == null) {
			return -1;
		}
		String zipName = user.getName() + ((user.getRole() != null && user.getRole() == 1)?"-销售大使二维码":"-推广大使二维码");
		List<ZipPictureEntryVo> zips = new ArrayList<>();
		// app下载连接
		
		zips.add(new ZipPictureEntryVo(QRCodeUtil.APP_URL+user.getId(), new ZipEntry("app下载.jpg")));
		// 查询以上架课程
		List<Book> books = generalUserDao.selectOnSelveBooks();
		
		for (Book book : books) {
			if(user.getRole()!=null && user.getRole()==0){
				zips.add(new ZipPictureEntryVo(new ShareBookUrlVo(book.getId(), userid,0).getUrl(),new ZipEntry(book.getName() + "体验码.jpg")));
			}else if(user.getRole()!=null && user.getRole()==1){
				zips.add(new ZipPictureEntryVo(new ShareBookUrlVo(book.getId(), userid,0).getUrl(),new ZipEntry(book.getName() + "体验码.jpg")));
				zips.add(new ZipPictureEntryVo(new ShareBookUrlVo(book.getId(), userid,1).getUrl(),new ZipEntry(book.getName() + "付费码.jpg")));
			}
			
		}
		try {
			ZipUtil.packagePicture(response,zipName , zips);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 1;
	}

	@Override
	public void exportPicture(HttpServletResponse response, Integer id) {
		CardProductRecord record = cardProductRecordDao.selectByPrimaryKey(id);
		String zipName=record.getCardNoStart()+"---"+record.getCardNoEnd();
		if(record.getType()!=null&&record.getType()==0){
			zipName+="付费卡";
		}else if(record.getType()!=null&&record.getType()==1){
			zipName+="半月卡";
		}
		List<ZipPictureEntryVo> zips = new ArrayList<>();
		List<CardRaw> cardRaws = icardRawService.listCardRaw(record.getCardNoStart(),record.getCardNoEnd());
		for(CardRaw cardraw : cardRaws){
			String qrcordUrl = "";
			if(record.getType()!=null&&record.getType()==0){
				 qrcordUrl = QRCodeUtil.CARD_URL_PAY+"?cardNumb="+cardraw.getCardNo()+"&cardCode="+cardraw.getCardCode();
			}else if(record.getType()!=null&&record.getType()==1){
				qrcordUrl = QRCodeUtil.CARD_URL_half+"?cardNumb="+cardraw.getCardNo()+"&cardCode="+cardraw.getCardCode();
			}
			String qrName=cardraw.getCardNo()+".jpg";
			zips.add(new ZipPictureEntryVo(qrcordUrl,new ZipEntry(qrName)));
		}
		try {
			ZipUtil.packagePicture(response,zipName , zips);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void exportHalfPicture(HttpServletResponse response, Integer id) {
		CardWholeSale record = cardWholeSaleDao.selectByPrimaryKey(id);

		List<CardRaw> cardRaws = icardRawService.listCardRaw(record.getCardNoStart(),record.getCardNoEnd());
		
		List<ZipPictureEntryVo> zips = new ArrayList<>();
		String zipName=record.getCardNoStart()+"---"+record.getCardNoEnd();
		for (CardRaw cardraw : cardRaws) {
			if(cardraw.getType()==0){
				continue;
			}
			String qrName=cardraw.getCardNo()+".jpg";
			String	qrcordUrl = QRCodeUtil.CARD_URL_half+"?cardNumb="+cardraw.getCardNo()+"&cardCode="+cardraw.getCardCode();
			zips.add(new ZipPictureEntryVo(qrcordUrl,new ZipEntry(qrName)));
		}
		try {
			ZipUtil.packagePicture(response,zipName , zips);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void exportCardPicture(HttpServletResponse response, Integer card_no) {
		Card card = new Card();
		card.setCardNo(card_no);
		card=cardMapper.selectOne(card);
		List<ZipPictureEntryVo> zips = new ArrayList<>();
		if(card!=null){
			String zipName=card.getCardNo()+"";
			String qrcordUrl = "";
			if(card.getType()!=null&&card.getType()==0){
				qrcordUrl = QRCodeUtil.CARD_URL_PAY+"?cardNumb="+card.getCardNo()+"&cardCode="+card.getCardCode();
			}else if(card.getType()!=null&&card.getType()==1){
				qrcordUrl = QRCodeUtil.CARD_URL_half+"?cardNumb="+card.getCardNo()+"&cardCode="+card.getCardCode();
			}
			String qrName=card.getCardNo()+".jpg";
			zips.add(new ZipPictureEntryVo(qrcordUrl,new ZipEntry(qrName)));
		}
		try {
			String zipName=card.getCardNo()+"";
			ZipUtil.packagePicture(response,zipName , zips);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
