package com.surelution.zjolap



import org.junit.*
import grails.test.mixin.*

@TestFor(WarningRuleController)
@Mock(WarningRule)
class WarningRuleControllerTests {

    def populateValidParams(params) {
        assert params != null
        // TODO: Populate valid properties like...
        //params["name"] = 'someValidName'
    }

    void testIndex() {
        controller.index()
        assert "/warningRule/list" == response.redirectedUrl
    }

    void testList() {

        def model = controller.list()

        assert model.warningRuleInstanceList.size() == 0
        assert model.warningRuleInstanceTotal == 0
    }

    void testCreate() {
        def model = controller.create()

        assert model.warningRuleInstance != null
    }

    void testSave() {
        controller.save()

        assert model.warningRuleInstance != null
        assert view == '/warningRule/create'

        response.reset()

        populateValidParams(params)
        controller.save()

        assert response.redirectedUrl == '/warningRule/show/1'
        assert controller.flash.message != null
        assert WarningRule.count() == 1
    }

    void testShow() {
        controller.show()

        assert flash.message != null
        assert response.redirectedUrl == '/warningRule/list'

        populateValidParams(params)
        def warningRule = new WarningRule(params)

        assert warningRule.save() != null

        params.id = warningRule.id

        def model = controller.show()

        assert model.warningRuleInstance == warningRule
    }

    void testEdit() {
        controller.edit()

        assert flash.message != null
        assert response.redirectedUrl == '/warningRule/list'

        populateValidParams(params)
        def warningRule = new WarningRule(params)

        assert warningRule.save() != null

        params.id = warningRule.id

        def model = controller.edit()

        assert model.warningRuleInstance == warningRule
    }

    void testUpdate() {
        controller.update()

        assert flash.message != null
        assert response.redirectedUrl == '/warningRule/list'

        response.reset()

        populateValidParams(params)
        def warningRule = new WarningRule(params)

        assert warningRule.save() != null

        // test invalid parameters in update
        params.id = warningRule.id
        //TODO: add invalid values to params object

        controller.update()

        assert view == "/warningRule/edit"
        assert model.warningRuleInstance != null

        warningRule.clearErrors()

        populateValidParams(params)
        controller.update()

        assert response.redirectedUrl == "/warningRule/show/$warningRule.id"
        assert flash.message != null

        //test outdated version number
        response.reset()
        warningRule.clearErrors()

        populateValidParams(params)
        params.id = warningRule.id
        params.version = -1
        controller.update()

        assert view == "/warningRule/edit"
        assert model.warningRuleInstance != null
        assert model.warningRuleInstance.errors.getFieldError('version')
        assert flash.message != null
    }

    void testDelete() {
        controller.delete()
        assert flash.message != null
        assert response.redirectedUrl == '/warningRule/list'

        response.reset()

        populateValidParams(params)
        def warningRule = new WarningRule(params)

        assert warningRule.save() != null
        assert WarningRule.count() == 1

        params.id = warningRule.id

        controller.delete()

        assert WarningRule.count() == 0
        assert WarningRule.get(warningRule.id) == null
        assert response.redirectedUrl == '/warningRule/list'
    }
}
