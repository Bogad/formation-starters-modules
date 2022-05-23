import codes from "./codes";

const { deleteCode, replaceCode, addCode } = codes;
/**
 * @param {String} code
 * @param {String} string
 */
const decodeString = (string, code) => {
  return string.replace(code, "");
};
/**
 * @param {Array} array
 */
const isCodedArray = array => {
  let lastItem = array[array.length - 1];
  if (!lastItem) return false;
  return (
    lastItem.value === deleteCode ||
    lastItem.value === replaceCode ||
    lastItem.value === addCode
  );
};

/**
 * Create html element
 * @param {String} type html element
 * @param {Object} config
 */
function createElement(type, config) {
  const htmlElement = document.createElement(type);

  if (config === undefined) {
    return htmlElement;
  }

  if (config.className) {
    htmlElement.className = config.className;
  }

  if (config.content) {
    htmlElement.textContent = config.content;
  }

  if (config.children) {
    config.children.forEach(el => {
      if (el !== null) {
        htmlElement.appendChild(el);
      }
    });
  }

  return htmlElement;
}

/**
 * @param {Object} node
 * @return {HTMLElement}
 */
function createExpandedElement(node) {
  let op = "";

  if (isCodedArray(node.children)) {
    let code = node.children.pop().value;
    switch (code) {
      case deleteCode:
        op = "deleted";
        break;
      case replaceCode:
        op = "replaced";
        break;
      case addCode:
        op = "added";
        break;

      default:
        break;
    }
  }
  const iElem = createElement("i");

  if (node.expanded) {
    iElem.className = "fa fa-caret-down";
  } else {
    iElem.className = "fa fa-caret-right";
  }

  const caretElem = createElement("div", {
    className: "caret-icon",
    children: [iElem]
  });

  const handleClick = node.toggle.bind(node);
  caretElem.addEventListener("click", handleClick);

  const indexElem = createElement("div", {
    className: "json-index",
    content: node.key
  });

  const typeElem = createElement("div", {
    className: "json-type",
    content: node.type
  });

  const keyElem = createElement("div", {
    className: "json-key",
    content: node.key
  });

  const sizeElem = createElement("div", {
    className: "json-size"
  });

  if (node.type === "array") {
    sizeElem.innerText = "[" + node.children.length + "]";
  } else if (node.type === "object") {
    sizeElem.innerText = "";
  }

  let lineChildren;
  if (node.key === null) {
    lineChildren = [caretElem, typeElem, sizeElem];
  } else if (node.parent.type === "array") {
    lineChildren = [caretElem, indexElem, sizeElem];
  } else {
    lineChildren = [caretElem, keyElem, sizeElem];
  }

  const lineElem = createElement("div", {
    className: `line ${op}`,
    children: lineChildren
  });

  if (node.depth > 0) {
    lineElem.style = "margin-left: " + node.depth * 30 + "px;";
  }

  return lineElem;
}

const heiglightParent = node => {
  let parent = node.parent;
  if (parent) {
    parent.elem.classList.add("changed");
    heiglightParent(parent);
  }
};

/**
 * @param {Object} node
 * @return {HTMLElement}
 */
function createNotExpandedElement(node) {
  const caretElem = createElement("div", {
    className: "empty-icon"
  });

  const keyElem = createElement("div", {
    className: "json-key",
    content: node.key
  });

  const separatorElement = createElement("div", {
    className: "json-separator",
    content: ":"
  });

  const valueType = " json-" + typeof node.value;
  let valueContent = String(node.value);
  let op = null;
  if (valueContent.includes(deleteCode)) {
    op = "deleted";
    valueContent = decodeString(valueContent, deleteCode);
  } else if (valueContent.includes(replaceCode)) {
    op = "replaced";
    valueContent = decodeString(valueContent, replaceCode);
  } else if (valueContent.includes(addCode)) {
    op = "added";
    valueContent = decodeString(valueContent, addCode);
  }

  const valueElement = createElement("div", {
    className: "json-value" + valueType,
    content: valueContent
  });

  const lineElem = createElement("div", {
    className: `line ${op ? op : ""}`,
    children: [caretElem, keyElem, separatorElement, valueElement]
  });

  if (node.depth > 0) {
    lineElem.style = "margin-left: " + node.depth * 30 + "px;";
  }

  setTimeout(() => {
    if (op) {
      heiglightParent(node);
    }
  }, 0);

  return lineElem;
}

/**
 * create tree node
 * @return {Object}
 */
function createNode() {
  return {
    key: null,
    parent: null,
    value: null,
    expanded: false,
    type: null,
    children: null,
    elem: null,
    depth: 0,

    setCaretIconRight() {
      const icon = this.elem.querySelector(".fa");
      icon.classList.replace("fa-caret-down", "fa-caret-right");
    },

    setCaretIconDown() {
      const icon = this.elem.querySelector(".fa");
      icon.classList.replace("fa-caret-right", "fa-caret-down");
    },

    hideChildren() {
      if (this.children !== null) {
        this.children.forEach(item => {
          item.elem.classList.add("hide");
          if (item.expanded) {
            item.hideChildren();
          }
        });
      }
    },

    showChildren() {
      if (this.children !== null) {
        this.children.forEach(item => {
          item.elem.classList.remove("hide");
          if (item.expanded) {
            item.showChildren();
          }
        });
      }
    },

    toggle: function() {
      if (this.expanded) {
        this.expanded = false;
        this.hideChildren();
        this.setCaretIconRight();
      } else {
        this.expanded = true;
        this.showChildren();
        this.setCaretIconDown();
      }
    }
  };
}

/**
 * Return variable type
 * @param {*} val
 */
function getType(val) {
  let type = typeof val;
  if (Array.isArray(val)) {
    type = "array";
  } else if (val === null) {
    type = "null";
  }
  return type;
}

/**
 * Recursively traverse json object
 * @param {Object} obj parsed json object
 * @param {Object} parent of object tree
 */
function traverseObject(obj, parent) {
  for (let key in obj) {
    const child = createNode();
    child.parent = parent;
    child.key = key;
    child.type = getType(obj[key]);
    child.depth = parent.depth + 1;
    child.expanded = false;

    if (parent.type === "array" && child.type === "object") {
      child.key = `${parent.key}-${+key + 1}`;
    }
    if (typeof obj[key] === "object") {
      child.children = [];
      parent.children.push(child);
      traverseObject(obj[key], child);
      child.elem = createExpandedElement(child);
    } else {
      child.value = obj[key];
      child.elem = createNotExpandedElement(child);
      parent.children.push(child);
    }
  }
}

/**
 * Create root of a tree
 * @param {Object} obj Json object
 * @return {Object}
 */
function createTree(obj) {
  const tree = createNode();
  tree.type = getType(obj);
  tree.children = [];
  tree.expanded = true;

  traverseObject(obj, tree);
  tree.elem = createExpandedElement(tree);

  return tree;
}

/**
 * Recursively traverse Tree object
 * @param {Object} node
 * @param {Callback} callback
 */
function traverseTree(node, callback) {
  callback(node);
  if (node.children !== null) {
    node.children.forEach(item => {
      traverseTree(item, callback);
    });
  }
}

/**
 * Render Tree object
 * @param {Object} tree
 * @param {HTMLElement} rootElem
 */
function render(tree, rootElem, root) {
  rootElem.innerHTML = "";

  traverseTree(tree, node => {
    if (!node.expanded) {
      node.hideChildren();
    }
    if (node.key == root) {
      node.showChildren();
      node.setCaretIconDown();
    }
    rootElem.appendChild(node.elem);
  });
}

/* Export jsonView object */
export default {
  /**
   * Render JSON into DOM container
   * @param {String} jsonData
   * @param {String} targetElem
   */
  format: function(parsedJson, targetElem, rootName) {
    let root = rootName ? rootName : Object.keys(parsedJson)[0];
    let tree = null;
    if (Object.keys(parsedJson).length == 1) {
      tree = createTree(parsedJson);
    } else {
      var obj = {};
      obj[root] = parsedJson;
      tree = createTree(obj);
    }

    tree = tree.children[0];
    render(tree, targetElem, root);
  }
};
