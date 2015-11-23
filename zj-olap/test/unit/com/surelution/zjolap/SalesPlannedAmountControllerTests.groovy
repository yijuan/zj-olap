package com.surelution.zjolap



import org.junit.*
import grails.test.mixin.*

@TestFor(SalesPlannedAmountController)
@Mock(SalesPlannedAmount)
class SalesPlannedAmountControllerTests {

    def populateValidParams(params) {
        assert params != null
        // TODO: Populate valid properties like...
        //params["name"] = 'someValidName'
    }

    void testIndex() {
        controller.index()
        assert "/salesPlannedAmount/list" == response.redirectedUrl
    }

    void testList() {

        def model = controller.list()

        assert model.salesPlannedAmountInstanceList.size() == 0
        assert model.salesPlannedAmountInstanceTotal == 0
    }

    void testCreate() {
        def model = controller.create()

        assert model.salesPlannedAmountInstance != null
    }

    void testSave() {
        controller.save()

        assert model.salesPlannedAmountInstance != null
        assert view == '/salesPlannedAmount/create'

        response.reset()

        populateValidParams(params)
        controller.save()

        assert response.redirectedUrl == '/salesPlannedAmount/show/1'
        assert controller.flash.message != null
        assert SalesPlannedAmount.count() == 1
    }

    void testShow() {
        controller.show()

        assert flash.message != null
        assert response.redirectedUrl == '/salesPlannedAmount/list'

        populateValidParams(params)
        def salesPlannedAmount = new SalesPlannedAmount(params)

        assert salesPlannedAmount.save() != null

        params.id = salesPlannedAmount.id

        def model = controller.show()

        assert model.salesPlannedAmountInstance == salesPlannedAmount
    }

    void testEdit() {
        controller.edit()

        assert flash.message != null
        assert response.redirectedUrl == '/salesPlannedAmount/list'

        populateValidParams(params)
        def salesPlannedAmount = new SalesPlannedAmount(params)

        assert salesPlannedAmount.save() != null

        params.id = salesPlannedAmount.id

        def model = controller.edit()

        assert model.salesPlannedAmountInstance == salesPlannedAmount
    }

    void testUpdate() {
        controller.update()

        assert flash.message != null
        assert response.redirectedUrl == '/salesPlannedAmount/list'

        response.reset()

        populateValidParams(params)
        def salesPlannedAmount = new SalesPlannedAmount(params)

        assert salesPlannedAmount.save() != null

        // test invalid parameters in update
        params.id = salesPlannedAmount.id
        //TODO: add invalid values to params object

        controller.update()

        assert view == "/salesPlannedAmount/edit"
        assert model.salesPlannedAmountInstance != null

        salesPlannedAmount.clearErrors()

        populateValidParams(params)
        controller.update()

        assert response.redirectedUrl == "/salesPlannedAmount/show/$salesPlannedAmount.id"
        assert flash.message != null

        //test outdated version number
        response.reset()
        salesPlannedAmount.clearErrors()

        populateValidParams(params)
        params.id = salesPlannedAmount.id
        params.version = -1
        controller.update()

        assert view == "/salesPlannedAmount/edit"
        assert model.salesPlannedAmountInstance != null
        assert model.salesPlannedAmountInstance.errors.getFieldError('version')
        assert flash.message != null
    }

    void testDelete() {
        controller.delete()
        assert flash.message != null
        assert response.redirectedUrl == '/salesPlannedAmount/list'

        response.reset()

        populateValidParams(params)
        def salesPlannedAmount = new SalesPlannedAmount(params)

        assert salesPlannedAmount.save() != null
        assert SalesPlannedAmount.count() == 1

        params.id = salesPlannedAmount.id

        controller.delete()

        assert SalesPlannedAmount.count() == 0
        assert SalesPlannedAmount.get(salesPlannedAmount.id) == null
        assert response.redirectedUrl == '/salesPlannedAmount/list'
    }
}
