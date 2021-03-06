var markdown = angular.module('markdown', []);
markdown.config(['$locationProvider', function($locationProvider) {
  $locationProvider.html5Mode({
    enabled: true,
    requireBase: false
  });
}]);

markdown.service('linktoggle', function() {
  this.add = function(elements) {
    var current = this;
    elements.css({
      'cursor': 'pointer'
    }) //
    .on('click', function() {
      current.act(this);
    });
  };
  this.stop = function(num, tag) {
    for (var n = parseInt(num); n >= 1; n--) {
      if (tag == ('H' + n)) { return true; }
    }
    return false;
  };
  this.act = function(htmlElement) {
    var elem = angular.element(htmlElement);
    var tagName = htmlElement.tagName.toUpperCase();
    if (tagName == 'H1') {
      elem.nextAll().show();
      return;
    }
    var current = this;
    var hn = tagName.replace(/^H/, '');
    var all = elem.nextAll();
    var length = all.length;
    for (var i = 0; i < length; i++) {
      var nelem = all.get(i);
      var ntag = nelem.tagName.toUpperCase();
      if (/H\d+/.test(ntag) && current.stop(hn, ntag)) {
        break;
      }
      $(nelem).toggle();
    }
  };
});

markdown.service('codecss', function() {
  this.link = function(param) {
    return angular.element('<a href="/example/asset/show.html?html=' //
            + encodeURIComponent(param) //
            + '" target="_blank">测试一下</a>');
  };
  this.add = function(elements) {
    var current = this;
    elements.each(function() {
      if (angular.element(this).hasClass('language-html')) {
        angular.element(this).parent().after(current.link(this.innerHTML));
      }
      // Prism framework to highlight element.
      Prism.highlightElement(this, true);
    });
  };
});

markdown.service('catalog', function() {
  this.make = function() {
    var html = '<div class="catalog">';
    angular.element('h1, h2, h3, h4, h5, h6').each(function() {
      var cur = angular.element(this);
      var id = cur.attr('id');
      if (id) {
        html += '<' + this.tagName + '>';
        html += '<a href="#' + id + '">' + cur.text() + '</a>';
        html += '</' + this.tagName + '>';
      }
    });
    html += '</div>';
    return html;
  };
});

markdown.controller('indexCtrl', ['$scope', '$location',
    function($scope, $location) {
      var file = $location.search().filePath;
      if (file) {
        file = '&filePath=' + file;
      } else {
        file = '';
      }
      $scope.http = {
        url: '/md/text?_=' + new Date().getTime() + file
      };
    }]);

markdown.directive('render', ['$http', 'linktoggle', 'codecss', 'catalog',
    function($http, linktoggle, codecss, catalog) {
      return {
        restrict: 'E',
        scope: {
          src: '='
        },
        template: '<article class="markdown-body"></article>',
        replace: true,
        link: function(scope, element, attrs) {
          $http({
            method: 'GET',
            url: scope.src
          }).then(function success(response) {
            element.html(response.data.html);
            codecss.add(element.find('code[class*="language-"]'));
            linktoggle.add(angular.element('h1, h2, h3, h4, h5, h6'));
            element.children().first().before(catalog.make());
          }, function error(response) {
            console.log('请求失败');
          });
        }
      };
    }]);