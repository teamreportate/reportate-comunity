const {override, fixBabelImports, addLessLoader} = require('customize-cra');
module.exports                                   = override(
	fixBabelImports('import', {
		libraryName     : 'antd',
		libraryDirectory: 'es',
		style           : true,
	}),
	addLessLoader({
		javascriptEnabled: true,
		modifyVars       : {
			'@primary-color'     : '#4C9FDC',
			'@secondary-color'   : '#A3BF49',
			'@border-radius-base': '2px'
		},
	})
);
// do stuff with the webpack config...


