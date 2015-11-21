jQuery(function ($) {
	'use strict';

	var App = {

		init: function () {
			this.addCalorieObject = {};
			this.editCalorieObject = {};
			this.filterObject = {};

            this.cacheElements();
			this.bindEvents();
            this.getCalories();
		},

        cacheElements: function () {
            this.$addCalorieForm = $("#add-form");
            this.$editCalorieForm = $("#edit-form");
    	    this.$filterForm = $("#filter-form");
			this.$editModal = $("#edit-modal");
			this.$editMeal = $("#edit-meal");
			this.$editNumberOfCalories = $("#edit-number-of-calories");
			this.$editDateTime = $("#edit-date-time");
            this.$calorieEntryTableBody = $("#calorie-entry-table-body");
        },

		bindEvents: function() {
			this.$addCalorieForm.find('input').on('keyup', function(event) {
				var target = $(event.target);
				this.addCalorieObject[target.attr('name')] = target.val();
			}.bind(this));

			this.$editCalorieForm.find('input').on('keyup', function(event) {
				var target = $(event.target);
				this.editCalorieObject[target.attr('name')] = target.val();
			}.bind(this));

			this.$filterForm.find('input').on('keyup', function(event) {
				var target = $(event.target);
				this.filterObject[target.attr('name')] = target.val();
			}.bind(this));

			this.bindAddCalorieForm();
            this.bindEditCalorieForm();
            this.bindFilterForm();
		},

		bindAddCalorieForm: function() {
            this.$addCalorieForm.submit(
                function(event) {
                    event.preventDefault();
                    this.addCalorie();
                }.bind(this)
            );
 		},

		addCalorie: function(calorieObject) {
            $.ajax({
                type: 'POST',
                url: '/api/calories?' + $.param(this.filterObject),
                contentType: "application/json; charset=utf-8",
                data: JSON.stringify(this.addCalorieObject)
            })
			.done(this.addCalorieDone.bind(this))
			.fail(this.addCalorieFail.bind(this));
		},
		addCalorieDone: function(data, textStatus, jqXHR) {
            this.clearForm(this.$addCalorieForm);
			this.addCalorieObject = {};
            successMsg("Calorie entry added.");
            this.buildCalorieEntryTable(data);
		},
		addCalorieFail: function(jqXHR, textStatus, errorThrown) {
            errorMsg("Unable to add calorie entry!");
            console.error(jqXHR);
        },

 		bindFilterForm: function() {
            this.$filterForm.submit(
                function(event) {
                    event.preventDefault();
                    this.getCalories();
                }.bind(this)
            );
 		},

		getCalories: function() {
			$.ajax({
				type: 'GET',
				url: '/api/calories?' + $.param(this.filterObject),
				contentType: "application/json; charset=utf-8"
			})
			.done(this.getCaloriesDone.bind(this))
			.fail(this.getCaloriesFail.bind(this));
		},
		getCaloriesDone: function(data, textStatus, jqXHR) {
            this.buildCalorieEntryTable(data);
		},
		getCaloriesFail: function(jqXHR, textStatus, errorThrown) {
            if (jqXHR.status === 403) {
                window.location = "login.html";
            } else {
                errorMsg("Unable to get calorie entries!");
                console.error(jqXHR);
            }
		},

 		bindEditCalorieForm: function() {
 			this.$editCalorieForm.submit(
 				function(event) {
 					event.preventDefault();
                    this.editCalorie();
 				}.bind(this)
 			);
 		},

		editCalorie: function() {
			$.ajax({
				type: 'PUT',
				url: '/api/calories',
				contentType: "application/json; charset=utf-8",
				data: JSON.stringify(this.editCalorieObject)
			})
			.done(this.editCalorieDone.bind(this))
			.fail(this.editCalorieFail.bind(this));
 		},
 		editCalorieDone: function(data, textStatus, jqXHR) {
			this.$editModal.modal('hide');
			this.updateCalorieRow(this.editCalorieObject);
			successMsg("Calorie entry edited.");
 		},
 		editCalorieFail: function(jqXHR, textStatus, errorThrown) {
			errorMsg("Unable to edit calorie entry!");
			console.error(jqXHR);
 		},
		updateCalorieRow: function(calorieEntry) {
			$("tr[data-id='" + calorieEntry.id + "']").replaceWith(this.createCalorieRow(calorieEntry));
		},

		buildCalorieEntryTable: function(data) {
	        console.log(data);
            this.$calorieEntryTableBody.html("");
            for (var i = 0; i < data.length; i++) {
                var calorieEntry = data[i];
                var tr = this.createCalorieRow(calorieEntry);
                tr.appendTo(this.$calorieEntryTableBody);
            }
		},

		bindOpenEdit: function(event) {
			event.preventDefault();

			var row = $(event.target).closest("tr");
			var tds = row.find("td");

			this.editCalorieObject.id = row.attr("data-id");
			this.editCalorieObject.meal = $(tds.get(0)).text();
			this.editCalorieObject.numberOfCalories = $(tds.get(1)).text();
			this.editCalorieObject.dateTime = $(tds.get(2)).text();

			this.$editMeal.val(this.editCalorieObject.meal);
			this.$editNumberOfCalories.val(this.editCalorieObject.numberOfCalories);
			this.$editDateTime.val(this.editCalorieObject.dateTime);

			this.$editModal.modal('show');
        },

        bindDelete: function(event) {
            $.ajax({
                type: 'DELETE',
                url: '/api/calories/' + this.getCalorieObjectIdToDelete(event),
                contentType: "application/json; charset=utf-8"
            })
            .done(function (data, textStatus, jqXHR) {
                this.deleteRowById(this.getCalorieObjectIdToDelete(event));
                successMsg("Calorie entry deleted!");
            }.bind(this))
            .fail(function (jqXHR, textStatus, errorThrown) {
                errorMsg("Unable to delete the calorie entry!");
                console.error(jqXHR);
            });
        },
        deleteRowById: function(id) {
            $("tr[data-id='" + id + "']").remove();
        },
        getCalorieObjectIdToDelete: function(event) {
            return $(event.target).closest("tr").attr("data-id");
        },

        clearForm: function(form) {
            form.find("input").val("");
        },

        createCalorieRow: function(calorieEntry) {
            var tr = $("<tr/>", { 'data-id':calorieEntry.id });
            $("<td/>", { 'text':calorieEntry.meal } ).appendTo(tr);
            $("<td/>", { 'text':calorieEntry.numberOfCalories } ).appendTo(tr);
            $("<td/>", { 'text':calorieEntry.dateTime } ).appendTo(tr);
            var lastColumn = $("<td/>").appendTo(tr);
            $("<a href=\"#\" class=\"edit\">Edit</a>").appendTo(lastColumn);
            lastColumn.append("&nbsp;");
            $("<a href=\"#\" class=\"delete\">Delete</a>").appendTo(lastColumn);

            tr.find(".edit").click(this.bindOpenEdit.bind(this));
            tr.find(".delete").click(this.bindDelete.bind(this));

            return tr;
        },

	};

	App.init();
});
