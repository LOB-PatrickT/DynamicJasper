/*
 * Dynamic Jasper: A library for creating reports dynamically by specifying
 * columns, groups, styles, etc. at runtime. It also saves a lot of development
 * time in many cases! (http://sourceforge.net/projects/dynamicjasper)
 *
 * Copyright (C) 2008  FDV Solutions (http://www.fdvsolutions.com)
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 *
 * License as published by the Free Software Foundation; either
 *
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 *
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 *
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 *
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301  USA
 *
 *
 */

package ar.com.fdvs.dj.test;

import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

import net.sf.jasperreports.view.JasperDesignViewer;
import net.sf.jasperreports.view.JasperViewer;
import ar.com.fdvs.dj.core.DynamicJasperHelper;
import ar.com.fdvs.dj.domain.AutoText;
import ar.com.fdvs.dj.domain.DynamicReport;
import ar.com.fdvs.dj.domain.builders.FastReportBuilder;

public class AutotextReportTest extends BaseDjReportTest {

	public DynamicReport buildReport() throws Exception {

		FastReportBuilder drb = new FastReportBuilder();
		drb.addColumn("State", "state", String.class.getName(),30)
			.addColumn("Branch", "branch", String.class.getName(),30)
			.addColumn("Product Line", "productLine", String.class.getName(),50)
			.addColumn("Item", "item", String.class.getName(),50)
			.addColumn("Item Code", "id", Long.class.getName(),30,true)
			.addColumn("Quantity", "quantity", Long.class.getName(),60,true)
			.addColumn("Amount", "amount", Float.class.getName(),70,true)
			.addGroups(2)
			.setTitle("November 2006 sales report")
			.setSubtitle("This report was generated at " + new Date())
			.setUseFullPageWidth(true);


		/**
		 * Adding many autotexts in the same position (header/footer and aligment) makes them to be one on top of the other
		 */
		//First add in the header
		drb.addAutoText(AutoText.AUTOTEXT_PAGE_X_OF_Y, AutoText.POSITION_FOOTER, AutoText.ALIGMENT_LEFT);
		drb.addAutoText("Autotext below Page counter", AutoText.POSITION_FOOTER, AutoText.ALIGMENT_LEFT);

		//Note the styled text: <b>msimone</b>, valid tags are: <b>, <i> and <u>
		drb.addAutoText("Created by <b>msimone</b>", AutoText.POSITION_FOOTER, AutoText.ALIGMENT_RIGHT);
		drb.addAutoText(AutoText.AUTOTEXT_PAGE_X_SLASH_Y, AutoText.POSITION_FOOTER, AutoText.ALIGMENT_RIGHT);

		drb.addAutoText(AutoText.AUTOTEXT_CREATED_ON, AutoText.POSITION_FOOTER, AutoText.ALIGMENT_LEFT,AutoText.PATTERN_DATE_DATE_TIME);

		//Now in FOOTER
		drb.addAutoText(AutoText.AUTOTEXT_PAGE_X_OF_Y, AutoText.POSITION_HEADER, AutoText.ALIGMENT_LEFT);
		drb.addAutoText("Autotext at top-left", AutoText.POSITION_HEADER, AutoText.ALIGMENT_LEFT);
		drb.addAutoText("Autotext at top-left (2)", AutoText.POSITION_HEADER, AutoText.ALIGMENT_LEFT);
		drb.addAutoText("Autotext at top-center", AutoText.POSITION_HEADER, AutoText.ALIGMENT_CENTER);
		DynamicReport dr = drb.build();

		//i18N, you can set a Locale, different than the default in the VM
//		drb.setReportLocale(new Locale("es","AR"));
		drb.setReportLocale(new Locale("pr","BR"));

		return dr;
	}

	public static void main(String[] args) throws Exception {
		AutotextReportTest test = new AutotextReportTest();
		test.testReport();
		JasperViewer.viewReport(test.jp);
		JasperDesignViewer.viewReportDesign(DynamicJasperHelper.generateJasperReport(test.dr, test.getLayoutManager(),new HashMap()));
	}

}