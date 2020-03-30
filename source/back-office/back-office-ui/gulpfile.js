/**
 * MC4
 * Santa Cruz - Bolivia
 * project: back-office-ui
 * date:    08-05-19
 * author:  fmontero
 **/

/**
 *configuracion gulp para generar un paquete war a partir del compilado
 *
 * ====================PREVIAMENTE INSTALAR============================
         "gulp": "^3.9.1",
         "gulp-clean": "^0.4.0",
         "gulp-war": "^0.1.4",
         "gulp-zip": "^4.2.0",
         "run-sequence": "^2.2.1",
 * ====================================================================
 */
const gulp = require('gulp');
const war = require('gulp-war');
const zip = require('gulp-zip');
const clean = require('gulp-clean');
const runSequence = require('run-sequence');

const warForlder='./distWar';
const warName='clic';
gulp.task('build:war', function (callback) {
  runSequence(
    'war:clean-dist',
    'war:build-war',
    callback);
});

gulp.task('war:clean-dist', function () {
  return gulp.src(warForlder, { read: false })
    .pipe(clean());
});

gulp.task('war:build-war', function () {
  gulp.src(["dist/**"])
    .pipe(war({
      welcome: 'index.html',
      displayName: warName
    }))
    .pipe(zip(warName+'.war'))
    .pipe(gulp.dest(warForlder));
});
